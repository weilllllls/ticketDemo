package com.product.ticket.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.product.ticket.bean.Result;
import com.product.ticket.entity.Ticket;
import com.product.ticket.entity.TicketHistory;
import com.product.ticket.service.impl.TicketServiceImpl;
import com.product.ticket.utils.IncrementNumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("v1/ticket")
public class TicketController {

    @Autowired
    TicketServiceImpl ticketService;

    @Autowired
    IncrementNumUtil incrementNumUtil;

    /**
     * 分页查询商票
     * @param pageNum
     * @param pageSize
     * @param giveWay
     * @param lock
     * @return
     */
    @GetMapping("ticketsByUid")
    public Result selectPage(@RequestParam("pageNum")int pageNum,
                             @RequestParam("pageSize")int pageSize,
                             @RequestParam(value = "mortgage",required = false)String mortgage,
                             @RequestParam(value = "lock",required = false)String lock,
                             @RequestParam(value = "price",required = false)String price,
                             @RequestParam(value = "startDate",required = false)String startDate,
                             @RequestParam(value = "endDate",required = false)String endDate,
                             @RequestParam(value = "ticketNum",required = false)String ticketNum,
                             @RequestParam(value = "userId",required = false)Long userId){
        //PageHelper MyBatis分页插件
        PageHelper.startPage(pageNum, pageSize);
        //查询数据库的票据
        List<Ticket> ticketPage = ticketService.selectList(mortgage,lock,userId,price,startDate,endDate,ticketNum);
        //PageInfo MyBatis分页助手
        PageInfo<Ticket> page = new PageInfo<Ticket>(ticketPage);
        Result result = new Result();
        result.setData(page);
        result.setMsg("查询成功");
        result.setSuccess(true);
        return result;
    }

    /**
     * 查收票人是登录用户的列表
     * @param pageNum
     * @param pageSize
     * @param giveWay
     * @param lock
     * @return
     */
    @GetMapping("recverList")
    public Result searchReceverList(@RequestParam("pageNum")int pageNum,
                                    @RequestParam("pageSize")int pageSize,
                                    @RequestParam(value = "mortgage",required = false)String mortgage,
                                    @RequestParam(value = "lock",required = false)String lock,
                                    @RequestParam(value = "price",required = false)String price,
                                    @RequestParam(value = "startDate",required = false)String startDate,
                                    @RequestParam(value = "endDate",required = false)String endDate,
                                    @RequestParam(value = "ticketNum",required = false)String ticketNum,
                                    @RequestParam(value = "userId",required = false)Long userId){
        PageHelper.startPage(pageNum, pageSize);
        List<Ticket> ticketPage = ticketService.selectReceverList(mortgage,lock,userId,price,startDate,endDate,ticketNum);
        PageInfo<Ticket> page = new PageInfo<Ticket>(ticketPage);
        Result result = new Result();
        result.setData(page);
        result.setMsg("查询成功");
        result.setSuccess(true);
        return result;

    }

//    /**
//     * 查收票人是登录用户的列表
//     * @param pageNum
//     * @param pageSize
//     * @param giveWay
//     * @param lock
//     * @return
//     */
//    @GetMapping("recverList")
//    public Result searchReceverList(@RequestParam("pageNum")int pageNum,
//                                    @RequestParam("pageSize")int pageSize,
//                                    @RequestParam("userId")Long userId){
//        PageHelper.startPage(pageNum, pageSize);
//        List<Ticket> ticketPage = ticketService.selectReceverList(userId);
//        PageInfo<Ticket> page = new PageInfo<Ticket>(ticketPage);
//        Result result = new Result();
//        result.setData(page);
//        result.setMsg("查询成功");
//        result.setSuccess(true);
//        return result;
//
//    }

    /**
     * 未确认
     * @param pageNum
     * @param pageSize
     * @param giveWay
     * @param lock
     * @return
     */
    @GetMapping("unConfirmList")
    public Result selectUnConfirmList(@RequestParam("pageNum")int pageNum,
                             @RequestParam("pageSize")int pageSize,
                             @RequestParam("userId")Long userId){
        PageHelper.startPage(pageNum, pageSize);
        List<Ticket> ticketPage = ticketService.selectUnConfirm(userId);
        PageInfo<Ticket> page = new PageInfo<Ticket>(ticketPage);
        Result result = new Result();
        result.setData(page);
        result.setMsg("查询成功");
        result.setSuccess(true);
        return result;
    }

    /**
     * 承兑失效列表
     * @param pageNum
     * @param pageSize
     * @param giveWay
     * @param lock
     * @return
     */
    @GetMapping("acceptanceFailure")
    public Result acceptanceFailure(@RequestParam("pageNum")int pageNum,
                                      @RequestParam("pageSize")int pageSize,
                                      @RequestParam("userId")Long userId){
        PageHelper.startPage(pageNum, pageSize);
        List<TicketHistory> ticketPage = ticketService.selectAcceptanceFailure(userId);
        PageInfo<TicketHistory> page = new PageInfo<TicketHistory>(ticketPage);
        Result result = new Result();
        result.setData(page);
        result.setMsg("查询成功");
        result.setSuccess(true);
        return result;
    }


    /**
     * 主键查询
     * @param ticketId
     * @return
     */
    @GetMapping("ticketsById")
    public Result selectOne(@RequestParam("ticketId")Long ticketId){
        Ticket ticket = ticketService.selectOne(ticketId);
        Result result = new Result();
        result.setData(ticket);
        result.setMsg("查询成功");
        result.setSuccess(true);
        return result;
    }

    /**
     * 提交商票
     * @param ticket
     * @return
     */
    @PostMapping("addTicket")
    public Result addTicket(@RequestBody String ticket){
        //参数为ticket的JSON字符串
        Result result = new Result();
        System.out.println(ticket);
        try {
            //将JSON字符串ticket转换为Ticket对象
            Ticket ticket1 = JSON.parseObject(ticket, Ticket.class);
            ticketService.addTicket(ticket1);
            result.setMsg("提交成功");
            result.setSuccess(true);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 确认新建商票
     * @param json
     * @return
     */
    @GetMapping("confirmTickets")
    public Result confirmTicket(@RequestParam Long ticketId){
        Result result = new Result();
        try {
            ticketService.confirmSubmit(ticketId);
            result.setMsg("确认成功");
            result.setSuccess(true);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 转让
     * @param ticketId
     * @return
     */
    @PostMapping("transferTicket")
    public Result transferTicket(@RequestBody JSONObject json){
        //商票id
        Long ticketId = json.getLong("ticketId");
        //用户id
        Long userId = json.getLong("userId");
        //名称
        Result result = new Result();
        try {
            ticketService.submitSwitch(userId,ticketId,json);
            result.setMsg("转让成功");
            result.setSuccess(true);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 转让确认
     * @param json
     * @return
     */
    @PostMapping("confirmSwitch")
    public Result confirmSwitch(@RequestBody JSONObject json){
        //商票id
        Long ticketId = json.getLong("ticketId");
        //用户id
        Long userId = json.getLong("userId");
        Result result = new Result();
        try {
            ticketService.confirmSwitch(userId,ticketId);
            result.setMsg("确认转让成功");
            result.setSuccess(true);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 转让未确认
     * @param pageNum
     * @param pageSize
     * @param giveWay
     * @param lock
     * @return
     */
    @GetMapping("switchUnConfirmList")
    public Result switchUnConfirmList(@RequestParam("pageNum")int pageNum,
                                      @RequestParam("pageSize")int pageSize,
                                      @RequestParam("userId")Long userId){
        PageHelper.startPage(pageNum, pageSize);
        List<Ticket> ticketPage = ticketService.selectSwitchUnConfirm(userId);
        PageInfo<Ticket> page = new PageInfo<Ticket>(ticketPage);
        Result result = new Result();
        result.setData(page);
        result.setMsg("查询成功");
        result.setSuccess(true);
        return result;
    }

    /**
     * 贴现
     * @param json
     * @return
     */
    @PostMapping("doDiscount")
    public Result doDiscount(@RequestBody JSONObject json){
        //商票id
        Long ticketId = json.getLong("ticketId");
        Long userId = json.getLong("userId");
        Result result = new Result();
        try {
            ticketService.doDiscount(ticketId,userId);
            result.setMsg("贴现成功");
            result.setSuccess(true);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 承兑
     * @param json
     * @return
     */
    @PostMapping("acceptance")
    public Result acceptance(@RequestBody JSONObject json){
        //商票id
        Long ticketId = json.getLong("ticketId");
        Long userId = json.getLong("userId");
        Result result = new Result();
        try {
            ticketService.acceptance(ticketId);
            result.setMsg("承兑成功");
            result.setSuccess(true);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 抵押未确认
     * @param pageNum
     * @param pageSize
     * @param giveWay
     * @param lock
     * @return
     */
    @GetMapping("MortgageUnConfirmList")
    public Result selectMortgageUnConfirmList(@RequestParam("pageNum")int pageNum,
                                      @RequestParam("pageSize")int pageSize,
                                      @RequestParam("userId")Long userId){
        PageHelper.startPage(pageNum, pageSize);
        List<Ticket> ticketPage = ticketService.selectUnConfirmMortgageList(userId);
        PageInfo<Ticket> page = new PageInfo<Ticket>(ticketPage);
        Result result = new Result();
        result.setData(page);
        result.setMsg("查询成功");
        result.setSuccess(true);
        return result;
    }


    /**
     * 抵押确认
     * @param json
     * @return
     */
    @PostMapping("confirmMortgage")
    public Result confirmMortgage(@RequestBody JSONObject json){
        //商票id
        Long ticketId = json.getLong("ticketId");
        //用户id
        Long userId = json.getLong("userId");
        Result result = new Result();
        try {
            ticketService.confirmMortgage(json);
            result.setMsg("确认成功");
            result.setSuccess(true);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 提交抵押
     * @param ticketId
     * @return
     */
    @PostMapping("submitMortgage")
    public Result submitMortgage(@RequestBody JSONObject json){
        Result result = new Result();
        try {
            ticketService.mortgage(json);
            result.setMsg("提交抵押成功");
            result.setSuccess(true);
        }catch (Exception e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }
}
