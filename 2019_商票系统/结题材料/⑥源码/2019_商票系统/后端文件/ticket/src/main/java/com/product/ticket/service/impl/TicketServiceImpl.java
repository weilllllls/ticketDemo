package com.product.ticket.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.product.ticket.entity.*;
import com.product.ticket.mapper.*;
import com.product.ticket.service.TicketService;
import com.product.ticket.utils.IncrementNumUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketMapper ticketMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    IncrementNumUtil incrementNumUtil;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    TicketHistoryMapper ticketHistoryMapper;

    @Autowired
    BankMapper bankMapper;

    /**
     * 查商票列表
     *
     * @param giveWay
     * @param lock
     * @param userId
     * @return
     */
    @Override
    public List<Ticket> selectList(String mortgage, String lock, Long userId, String price, String startDate, String endDate, String ticketNum) {
        List<Ticket> tickets = ticketMapper.selectTickets(userId, mortgage, lock, ticketNum, price, startDate, endDate);
        return tickets;
    }

    /**
     * 查收票人是登录用户的列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Ticket> selectReceverList(String mortgage, String lock, Long userId, String price, String startDate, String endDate, String ticketNum) {
        List<Ticket> tickets = ticketMapper.selectReceverTickets(userId, mortgage, lock, ticketNum, price, startDate, endDate);
        return tickets;
    }

//    /**
//     * 查收票人是登录用户的列表
//     *
//     * @param userId
//     * @return
//     */
//    @Override
//    @Transactional
//    public List<Ticket> selectReceverList(Long userId) {
//        List<Ticket> receive_id = ticketMapper.selectList(new QueryWrapper<Ticket>().eq("receive_id", userId).eq("confirm", 1));
//        return receive_id;
//    }

    /**
     * 查未确认
     *
     * @param userId
     * @return
     */
    @Override
    public List<Ticket> selectUnConfirm(Long userId) {
        List<Ticket> tickets = ticketMapper.selectUnconfirmTickets(userId);
        return tickets;
    }


    /**
     * 主键查询
     *
     * @param ticketId
     * @return
     */
    @Override
    public Ticket selectOne(Long ticketId) {
        return ticketMapper.selectOne(new QueryWrapper<Ticket>().eq("id", ticketId));
    }


    /**
     * 提交商票
     *
     * @param ticket
     */
    @Override
    @Transactional
    public void addTicket(Ticket ticket) {
        //参数ticket为Ticket对象
        //校验出票人，收款人，承兑人
        User name = userMapper.selectOne(new QueryWrapper<User>().eq("name", ticket.getIssueUser()));
        if (name == null) {
            throw new RuntimeException("出票人匹配失败，请填写正确的出票人信息");
        }
        User name1 = userMapper.selectOne(new QueryWrapper<User>().eq("name", ticket.getReceiveUser()));
        if (name1 == null) {
            throw new RuntimeException("收款人匹配失败，请填写正确的收款人信息");
        } else {
            ticket.setReceiveId(name1.getId());
        }
        //匹配开户行
        Bank bank1 = bankMapper.selectOne(new QueryWrapper<Bank>().eq("name", ticket.getIssueBank()));
        if (bank1 == null) {
            throw new RuntimeException("出票人开户行名称与系统中银行不匹配，请检查");
        }
        Bank bank2 = bankMapper.selectOne(new QueryWrapper<Bank>().eq("name", ticket.getAcceptancerBank()));
        if (bank2 == null) {
            throw new RuntimeException("承兑人开户行名称与系统中银行不匹配，请检查");
        } else {
            if (!bank2.getNum().equals(ticket.getAcceptancerBankNum())) {
                throw new RuntimeException("承兑人开户行名称与系统中银行行号不匹配，请检查");
            }
        }
        Bank bank3 = bankMapper.selectOne(new QueryWrapper<Bank>().eq("name", ticket.getReceiveBank()));
        if (bank3 == null) {
            throw new RuntimeException("收票人开户行名称与系统中银行不匹配，请检查");
        }

        User name2 = userMapper.selectOne(new QueryWrapper<User>().eq("name", ticket.getAcceptancerUser()));
        if (name2 == null) {
            throw new RuntimeException("承兑人匹配失败，请填写正确的承兑人信息");
        }
        String incrementNum = incrementNumUtil.getIncrementNum();
        //票据号码随机生成
        ticket.setTicketNum(incrementNum);
        ticket.setConfirm(0);
        ticket.setSwitchConfirm(null);
        ticket.setDiscountState(0);
        ticket.setMortgage("未抵押");
        ticket.setLockState("未锁定");
        ticket.setAcceptanceState(0);
        ticketMapper.insert(ticket);
    }


    /**
     * 确认提交
     *
     * @param ticketId
     */
    @Override
    @Transactional
    public void confirmSubmit(Long ticketId) {
        Ticket ticket = ticketMapper.selectOne(new QueryWrapper<Ticket>().eq("id", ticketId));
        ticket.setConfirm(1);
        ticketMapper.updateById(ticket);
    }

    /**
     * 提交转让
     *
     * @param ticketId
     * @param temp
     */
    @Override
    @Transactional
    public void submitSwitch(Long userId, Long ticketId, JSONObject temp) {
        Ticket ticket = ticketMapper.selectOne(new QueryWrapper<Ticket>().eq("id", ticketId));
        if (ticket.getSwitchInfo() != null) {
            throw new RuntimeException("转让申请已提交，不能重复提交");
        }
        if (ticket.getConfirm() == 0) {
            throw new RuntimeException("该商票未被收票人确认，提交失败");
        }
        if (ticket.getGiveWay().equals("不可转让")) {
            throw new RuntimeException("该商票不可转让，提交失败");
        }
        if (ticket.getMortgage().equals("已抵押")) {
            throw new RuntimeException("该商票已抵押，提交失败");
        }
        //匹配开户行

        Bank bank2 = bankMapper.selectOne(new QueryWrapper<Bank>().eq("name", temp.getString("bankName")));
        if (bank2 == null) {
            throw new RuntimeException("承兑人开户行名称与系统中银行不匹配，请检查");
        } else {
            //System.out.println(bank2);
            if (!bank2.getNum().equals(temp.getString("bankNum"))) {
                throw new RuntimeException("承兑人开户行名称与系统中银行行号不匹配，请检查");
            }
        }
        //temp为swithInfo中提交给transferTicket的json
        String name = temp.getString("name");
        //name name1为被转让人的名字
        User name1 = userMapper.selectOne(new QueryWrapper<User>().eq("name", name));
        if (name1 == null) {
            throw new RuntimeException("匹配不到系统中的被转让人姓名，请重新填写");
        } else {
            ticket.setSwitchTempId(name1.getId());
        }
        //存缓存
        ticket.setSwitchInfo(JSON.toJSONString(temp));
        ticket.setSwitchConfirm(0);
        ticketMapper.updateById(ticket);

    }

    /**
     * 转让确认
     *
     * @param userId
     * @param ticketId
     */
    @Override
    @Transactional
    public void confirmSwitch(Long userId, Long ticketId) {
        Ticket ticket = ticketMapper.selectOne(new QueryWrapper<Ticket>().eq("id", ticketId));
        //获取转让信息
        String switchInfo = ticket.getSwitchInfo();
        JSONObject json = JSON.parseObject(switchInfo);
        String name = json.getString("name");
        //转让金额
        String price = json.getString("price");
        //银行账户名
        String accountName = json.getString("accountName");
        //开户行
        String bankName = json.getString("bankName");
        //行号
        String bankNum = json.getString("bankNum");
        //置换收票人
//        ticket.setIssueAccount(accountName);
//        ticket.setIssueBank(bankName);
//        ticket.setIssueUser(name);
        ticket.setReceiveAccount(accountName);
        ticket.setReceiveBank(bankName);
        ticket.setReceiveUser(name);
        User name1 = userMapper.selectOne(new QueryWrapper<User>().eq("name", name));
        //ticket.setUserId(name1.getId());
        ticket.setReceiveId(name1.getId());
        ticket.setSwitchConfirm(1);
        ticketMapper.updateById(ticket);
    }

    /**
     * 查转让未确认
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public List<Ticket> selectSwitchUnConfirm(Long userId) {
        return ticketMapper.selectList(new QueryWrapper<Ticket>().eq("switch_temp_id", userId).eq("switch_confirm", 0));
    }

    /**
     * 贴现
     *
     * @param ticketId
     */
    @Override
    @Transactional
    public void doDiscount(Long ticketId, Long userId) throws ParseException {
        Ticket ticket = ticketMapper.selectOne(new QueryWrapper<Ticket>().eq("id", ticketId));
        Date ensureDate = ticket.getExpireDate();
        if (compare(ensureDate)) {
            throw new RuntimeException("贴现失败，该商票已过期");
        }
        if (ticket.getConfirm() == 0) {
            throw new RuntimeException("贴现失败，该商票未被收票人确认");
        }
        if (ticket.getSwitchInfo() != null) {
            if (ticket.getSwitchConfirm() == 0) {
                throw new RuntimeException("贴现失败，该商票未被转让人确认");
            }
        }
        if (ticket.getMortgage().equals("已抵押")) {
            throw new RuntimeException("贴现失败,该商票已抵押");
        }
        //设置已贴现
        ticket.setDiscountState(1);

        //获取相差天数
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String eDate = sdf.format(ensureDate);
        String cDate = sdf.format(currentDate);
        int i = differentDays(new Date(), ensureDate);

        //计算利息

        //贴现天数
        String days = String.valueOf(i);
        BigDecimal decimal1 = new BigDecimal(days);
        //贴现率
        String discountRate = "0.6";
        BigDecimal decimal2 = new BigDecimal(discountRate);
        //票面金额
        String price = ticket.getPrice();
        BigDecimal decimal3 = new BigDecimal(price);

        BigDecimal res = decimal3.multiply(decimal1);
        BigDecimal day = new BigDecimal("360");
        BigDecimal res1 = res.divide(day, 2, RoundingMode.HALF_UP);

        //利息
        BigDecimal interest = res1.multiply(decimal2).setScale(2, BigDecimal.ROUND_HALF_UP);

        //贴现返给出票人的金额
        BigDecimal subtract = decimal3.subtract(interest).setScale(2, BigDecimal.ROUND_HALF_UP);

        //转账
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", userId));
        String balance = user.getBalance();
        BigDecimal balanceDec = new BigDecimal(balance);
        BigDecimal add = balanceDec.add(subtract);
        user.setBalance(add.toString());
        userMapper.updateById(user);

        //将票据转让给银行
        ticket.setReceiveAccount("90000000000");
        ticket.setReceiveBank("中央银行");
        ticket.setReceiveUser("银行");
        User name = userMapper.selectOne(new QueryWrapper<User>().eq("name", "银行"));
        //ticket.setUserId(name1.getId());
        ticket.setReceiveId(name.getId());
        //贴现转让给银行直接确认转让 不需要银行登录再确认
        ticket.setSwitchConfirm(1);
        ticketMapper.updateById(ticket);
    }


    //判断是否过期
    private boolean compare(Date ensureDate) throws ParseException {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = sdf.format(ensureDate);
        String format1 = sdf.format(currentDate);
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //将字符串形式的时间转化为Date类型的时间
        Date a = sdf1.parse(format);
        //System.out.println(format);
        Date b = sdf1.parse(format1);
        //System.out.println(format1);
        //Date类的一个方法，如果a早于b返回true，否则返回false
        if (a.before(b))
            //已过期
            return true;
        else
            //未过期
            return false;
    }


    /**
     * 计算相差天数
     *
     * @param oldDate
     * @param nowDate
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    //加一月
    private String subMonth(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = sdf.parse(date);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.MONTH, 1);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }


    /**
     * 承兑
     *
     * @param ticketId
     */
    @Override
    @Transactional
    public void acceptance(Long ticketId) throws ParseException {
        Ticket ticket = ticketMapper.selectOne(new QueryWrapper<Ticket>().eq("id", ticketId));
        if (ticket.getAcceptanceState() == 1) {
            throw new RuntimeException("承兑失败，该商票已被承兑");
        }
        if (ticket.getConfirm() == 0) {
            throw new RuntimeException("承兑失败，该商票未被收票人确认");
        }
        if (ticket.getSwitchInfo() != null) {
            if (ticket.getSwitchConfirm() == 0) {
                throw new RuntimeException("承兑失败，该商票未被转让人确认");
            }
        }
        if (ticket.getMortgage().equals("已抵押")) {
            throw new RuntimeException("承兑失败,该商票已抵押");
        }
        //到期日
        Date expireDate = ticket.getExpireDate();
        Date currentDate = new Date();
        //compare判断
        boolean compare = compare(expireDate);
        if (compare) {
            //判断是否在一个月内
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = sdf.format(expireDate);
            String res = subMonth(format);
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmt.parse(res);
            boolean compare1 = compare(date);
            if (!compare1) {
                //继续判断
                String price = ticket.getPrice();
                BigDecimal ticketPrice = new BigDecimal(price);
                //承兑人减钱
                User send = userMapper.selectOne(new QueryWrapper<User>().eq("name", ticket.getAcceptancerUser()));
                BigDecimal sendDecimal = new BigDecimal(send.getBalance());
                BigDecimal subtract = sendDecimal.subtract(ticketPrice);
                send.setBalance(subtract.toString());
                //判断负值
                BigDecimal conpare = new BigDecimal("0");
                if (subtract.compareTo(conpare) == -1) {
                    //减扣信用值
                    int credit = send.getCredit();
                    credit -= 10;
                    send.setCredit(credit);
                }
                //收票人加钱
                User recive = userMapper.selectOne(new QueryWrapper<User>().eq("id", ticket.getReceiveId()));
                String balance = recive.getBalance();
                BigDecimal subtract1 = new BigDecimal(balance);
                BigDecimal add = subtract1.add(ticketPrice);
                recive.setBalance(add.toString());

                Message message = new Message();
                message.setDate(new Date());
                message.setUid(send.getId());
                message.setMessage("承兑扣款:¥" + ticketPrice.toString() + "</br>收票人:" + recive.getName() + "</br>商票号:" + ticket.getTicketNum());
                messageMapper.insert(message);
                Message message1 = new Message();
                message1.setDate(new Date());
                message1.setUid(recive.getId());
                message1.setMessage("承兑收款:¥" + ticketPrice.toString() + "</br>出票人:" + send.getName() + "</br>商票号:" + ticket.getTicketNum());
                messageMapper.insert(message1);
                userMapper.updateById(send);
                userMapper.updateById(recive);
                ticket.setAcceptanceState(1);
                ticketMapper.updateById(ticket);
            } else {
                //作废商票
                ticket.setAcceptanceState(1);
                TicketHistory ticketHistory = new TicketHistory();
                BeanUtils.copyProperties(ticket, ticketHistory);
                ticketHistory.setDetail("到期超过一个月,商票作废");
                ticketMapper.deleteById(ticket.getId());
                //System.out.println("ticketHistory :"+ticketHistory);
                //ticketHistory成功产生了 应该是insert方法失败了
                ticketHistoryMapper.insert(ticketHistory);
                throw new RuntimeException("承兑失败，到期超过一个月");
            }
        } else {
            throw new RuntimeException("承兑失败，该商票未到期");
        }

    }

    /**
     * 查承兑失效列表
     *
     * @param uid
     * @return
     */
    @Override
    public List<TicketHistory> selectAcceptanceFailure(Long uid) {
        List<TicketHistory> ticketHistories = ticketHistoryMapper.selectList(new QueryWrapper<TicketHistory>().eq("user_id", uid).eq("type", 1));
        return ticketHistories;
    }


    /**
     * 申请抵押
     *
     * @param json
     */
    @Override
    @Transactional
    public void mortgage(JSONObject json) throws ParseException {
        //抵押人名称
        String dkName = json.getString("dkName");
        //抵押人银行账户
        String dkAccount = json.getString("dkAccount");
        //抵押人银行
        String dkBank = json.getString("dkBank");
        //抵押人开户行号
        String dkBankNum = json.getString("dkBankNum");
        //贷款金额
        String dkPrice = json.getString("dkPrice");
        //贷款日期
        Date dkDate = json.getDate("dkDate");
        //商票id
        Long ticketId = json.getLong("ticketId");
        Ticket ticket = ticketMapper.selectOne(new QueryWrapper<Ticket>().eq("id", ticketId));

        //抵押人
        User name = userMapper.selectOne(new QueryWrapper<User>().eq("name", dkName));
        if (name == null) {
            throw new RuntimeException("抵押人匹配失败，请填写正确的抵押人信息");
        }
        Bank bank2 = bankMapper.selectOne(new QueryWrapper<Bank>().eq("name", dkBank));
        if (bank2 == null) {
            throw new RuntimeException("抵押人开户行名称与系统中银行不匹配，请检查");
        } else {
            if (!bank2.getNum().equals(dkBankNum)) {
                throw new RuntimeException("抵押人开户行名称与系统中银行行号不匹配，请检查");
            }
        }
        if (ticket.getConfirm() == 0) {
            throw new RuntimeException("抵押失败，该商票未被收票人确认");
        }
        if (ticket.getSwitchInfo() != null) {
            if (ticket.getSwitchConfirm() == 0) {
                throw new RuntimeException("抵押失败，该商票未被转让人确认");
            }
        }
        if (ticket.getMortgage().equals("已抵押")) {
            throw new RuntimeException("抵押失败,该商票已抵押");
        }
        Date ensureDate = ticket.getExpireDate();
        if (compare(ensureDate)) {
            throw new RuntimeException("抵押失败，该商票已过期");
        }
        //判断贷款金额
        BigDecimal ticketPrice = new BigDecimal(ticket.getPrice());
        BigDecimal dkPriceDec = new BigDecimal(dkPrice);
        int a = ticketPrice.compareTo(dkPriceDec);
        if (a == -1) {
            throw new RuntimeException("贷款金额不能大于票面金额");
        }
        //判断日期
        if (differentDays(dkDate,ticket.getExpireDate()) <= 0) {
            throw new RuntimeException("贷款日期已经过期");
        } else {
            int days = differentDays(dkDate,ticket.getExpireDate());
            //在到日期的前一个月内
            if (days <= 30) {
                throw new RuntimeException("贷款日期不能在到期前一个月内");
            }
        }

        ticket.setDkUser(dkName);
        ticket.setDkUserId(name.getId());
        ticket.setDkAccount(dkAccount);
        ticket.setDkBank(dkBank);
        ticket.setDkBankNum(dkBankNum);
        ticket.setDkDate(dkDate);
        ticket.setDkPrice(dkPrice);
        ticket.setDkConfirm(0);
        ticketMapper.updateById(ticket);
        //贷款完成

    }

    /**
     * 确认抵押
     *
     * @param json
     * @throws ParseException
     */
    @Override
    @Transactional
    public void confirmMortgage(JSONObject json) throws ParseException {
        Long ticketId = json.getLong("ticketId");
        Ticket ticket = ticketMapper.selectOne(new QueryWrapper<Ticket>().eq("id", ticketId));
        //贷款人减贷款金额 贷款人是放钱的
        //User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", ticket.getUserId()));
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", ticket.getDkUserId()));
        BigDecimal balance = new BigDecimal(user.getBalance());
        BigDecimal dkPriceDec = new BigDecimal(ticket.getDkPrice());
        //BigDecimal add = balance.add(dkPriceDec);
        //user.setBalance(add.toString());
        BigDecimal subtract = balance.subtract(dkPriceDec);
        user.setBalance(subtract.toString());
        //抵押人加贷款金额 抵押人是借钱的
        //User user1 = userMapper.selectOne(new QueryWrapper<User>().eq("id", ticket.getDkUserId()));
        User user1 = userMapper.selectOne(new QueryWrapper<User>().eq("id", ticket.getReceiveId()));
        BigDecimal balance1 = new BigDecimal(user1.getBalance());
//        BigDecimal subtract = balance.subtract(dkPriceDec);
//        user.setBalance(subtract.toString());
        BigDecimal add = balance1.add(dkPriceDec);
        user1.setBalance(add.toString());
        //发消息
        Message message = new Message();
        message.setDate(new Date());
        message.setUid(user.getId());
        message.setMessage("抵押(贷款)扣款:¥" + dkPriceDec.toString() + "</br>贷款人:" + user.getName() + "</br>商票号:" + ticket.getTicketNum());
        messageMapper.insert(message);

        Message message1 = new Message();
        message1.setDate(new Date());
        message1.setUid(user1.getId());
        message1.setMessage("抵押(贷款)收款:¥" + dkPriceDec.toString() + "</br>抵押人:" + user1.getName() + "</br>商票号:" + ticket.getTicketNum());
        ticket.setMortgage("已抵押");
        ticket.setDkConfirm(1);
        ticketMapper.updateById(ticket);
        messageMapper.insert(message1);

        //更新user信息
        userMapper.updateById(user);
        userMapper.updateById(user1);

    }

    /**
     * 查抵押未确认列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<Ticket> selectUnConfirmMortgageList(Long userId) {
        List<Ticket> tickets = ticketMapper.selectList(new QueryWrapper<Ticket>().eq("dk_user_id", userId).eq("dk_confirm", 0));
        return tickets;
    }


}
