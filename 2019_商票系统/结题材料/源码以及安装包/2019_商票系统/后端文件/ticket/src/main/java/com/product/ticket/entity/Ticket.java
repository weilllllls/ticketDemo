package com.product.ticket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

@TableName("tkt_ticket")
public class Ticket implements Serializable {

    //主键
    @TableId(value = "id", type = IdType.AUTO)
    private long id;

    //出票日期
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date issueDate;

    //汇票到期日
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private  Date expireDate;

    //票据号码
    private  String ticketNum;

    //票据状态
    private  String ticketState;

    //金额
    private  String price;

    //出票人-全称
    private  String issueUser;

    //出票人-银行账号
    private  String issueAccount;

    //出票人-开户行
    private  String issueBank;

    //收票人-全称
    private  String receiveUser;

    //收票人-银行账号
    private  String receiveAccount;

    //收票人-开户行
    private  String receiveBank;

    //出票保证信息-保证人姓名
    private  String ensurerName;

    //出票保证信息-保证人姓名
    private  String ensurerAddress;

    //出票保证信息-保证日期
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private  Date ensureDate;

    //承兑人信息-全称
    private  String acceptancerUser;

    //承兑人信息-银行账号
    private  String acceptancerAccount;

    //承兑人信息-开户行
    private  String acceptancerBank;

    //承兑人信息-开户行行号
    private  String acceptancerBankNum;

    //能否转让
    private  String giveWay;

    //交易合同号
    private  String contractNumber;

    //出票人承诺
    private  String issuePromise;

    //承兑人承兑
    private  String acceptancerAcceptance;

    //抵押状态
    private String mortgage;

    //系统-创建人id
    private Long userId;

    //是否锁定
    private String lockState;

    //收票人id
    private Long receiveId;

    //确认
    private int confirm;

    //转让确认
    private Integer switchConfirm;

    //转让信息
    private String switchInfo;

    //缓存被转让用户id
    private Long switchTempId;

    //所属用户id
    private Long ownerId;
    //以下数据库没有
    //贴现状态
    private Integer discountState;

    //承兑状态
    private Integer acceptanceState;

    //贷款用户全名
    private String dkUser;

    //贷款银行账户
    private String dkAccount;

    //贷款银行
    private String dkBank;

    //贷款行号
    private String dkBankNum;

    //贷款金额
    private String dkPrice;

    private Long dkUserId;

    private Integer dkConfirm;

    //贷款日期
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date dkDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getTicketState() {
        return ticketState;
    }

    public void setTicketState(String ticketState) {
        this.ticketState = ticketState;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIssueUser() {
        return issueUser;
    }

    public void setIssueUser(String issueUser) {
        this.issueUser = issueUser;
    }

    public String getIssueAccount() {
        return issueAccount;
    }

    public void setIssueAccount(String issueAccount) {
        this.issueAccount = issueAccount;
    }

    public String getIssueBank() {
        return issueBank;
    }

    public void setIssueBank(String issueBank) {
        this.issueBank = issueBank;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(String receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    public String getReceiveBank() {
        return receiveBank;
    }

    public void setReceiveBank(String receiveBank) {
        this.receiveBank = receiveBank;
    }

    public String getEnsurerName() {
        return ensurerName;
    }

    public void setEnsurerName(String ensurerName) {
        this.ensurerName = ensurerName;
    }

    public String getEnsurerAddress() {
        return ensurerAddress;
    }

    public void setEnsurerAddress(String ensurerAddress) {
        this.ensurerAddress = ensurerAddress;
    }

    public Date getEnsureDate() {
        return ensureDate;
    }

    public void setEnsureDate(Date ensureDate) {
        this.ensureDate = ensureDate;
    }

    public String getAcceptancerUser() {
        return acceptancerUser;
    }

    public void setAcceptancerUser(String acceptancerUser) {
        this.acceptancerUser = acceptancerUser;
    }

    public String getAcceptancerAccount() {
        return acceptancerAccount;
    }

    public void setAcceptancerAccount(String acceptancerAccount) {
        this.acceptancerAccount = acceptancerAccount;
    }

    public String getAcceptancerBank() {
        return acceptancerBank;
    }

    public void setAcceptancerBank(String acceptancerBank) {
        this.acceptancerBank = acceptancerBank;
    }

    public String getAcceptancerBankNum() {
        return acceptancerBankNum;
    }

    public void setAcceptancerBankNum(String acceptancerBankNum) {
        this.acceptancerBankNum = acceptancerBankNum;
    }

    public String getGiveWay() {
        return giveWay;
    }

    public void setGiveWay(String giveWay) {
        this.giveWay = giveWay;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getIssuePromise() {
        return issuePromise;
    }

    public void setIssuePromise(String issuePromise) {
        this.issuePromise = issuePromise;
    }

    public String getAcceptancerAcceptance() {
        return acceptancerAcceptance;
    }

    public void setAcceptancerAcceptance(String acceptancerAcceptance) {
        this.acceptancerAcceptance = acceptancerAcceptance;
    }

    public String getMortgage() {
        return mortgage;
    }

    public void setMortgage(String mortgage) {
        this.mortgage = mortgage;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLockState() {
        return lockState;
    }

    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    public Long getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Long receiveId) {
        this.receiveId = receiveId;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    public Integer getSwitchConfirm() {
        return switchConfirm;
    }

    public void setSwitchConfirm(Integer switchConfirm) {
        this.switchConfirm = switchConfirm;
    }

    public String getSwitchInfo() {
        return switchInfo;
    }

    public void setSwitchInfo(String switchInfo) {
        this.switchInfo = switchInfo;
    }

    public Long getSwitchTempId() {
        return switchTempId;
    }

    public void setSwitchTempId(Long switchTempId) {
        this.switchTempId = switchTempId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getDiscountState() {
        return discountState;
    }

    public void setDiscountState(Integer discountState) {
        this.discountState = discountState;
    }


    public Integer getAcceptanceState() {
        return acceptanceState;
    }

    public void setAcceptanceState(Integer acceptancerState) {
        this.acceptanceState = acceptancerState;
    }


    public String getDkUser() {
        return dkUser;
    }

    public void setDkUser(String dkUser) {
        this.dkUser = dkUser;
    }

    public String getDkAccount() {
        return dkAccount;
    }

    public void setDkAccount(String dkAccount) {
        this.dkAccount = dkAccount;
    }

    public String getDkBank() {
        return dkBank;
    }

    public void setDkBank(String dkBank) {
        this.dkBank = dkBank;
    }

    public String getDkBankNum() {
        return dkBankNum;
    }

    public void setDkBankNum(String dkBankNum) {
        this.dkBankNum = dkBankNum;
    }

    public String getDkPrice() {
        return dkPrice;
    }

    public void setDkPrice(String dkPrice) {
        this.dkPrice = dkPrice;
    }

    public Date getDkDate() {
        return dkDate;
    }

    public void setDkDate(Date dkDate) {
        this.dkDate = dkDate;
    }

    public Long getDkUserId() {
        return dkUserId;
    }

    public void setDkUserId(Long dkUserId) {
        this.dkUserId = dkUserId;
    }

    public Integer getDkConfirm() {
        return dkConfirm;
    }

    public void setDkConfirm(Integer dkConfirm) {
        this.dkConfirm = dkConfirm;
    }
}
