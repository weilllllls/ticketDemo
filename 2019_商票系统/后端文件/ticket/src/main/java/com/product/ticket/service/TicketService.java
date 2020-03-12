package com.product.ticket.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.product.ticket.entity.Ticket;
import com.product.ticket.entity.TicketHistory;

import java.text.ParseException;
import java.util.List;

public interface TicketService {

    public List<Ticket> selectList(String mortgage, String lock,Long userId,String price, String startDate, String endDate,String ticketNum);

    public List<Ticket> selectReceverList(String mortgage, String lock,Long userId,String price, String startDate, String endDate,String ticketNum);

    public List<Ticket> selectUnConfirm(Long userId);

    public Ticket selectOne(Long ticketId);

    public void addTicket(Ticket ticket);

    public void confirmSubmit(Long ticketId);

    public void submitSwitch(Long userId,Long ticketId, JSONObject temp);

    public void confirmSwitch(Long userId,Long ticketId);

    public List<Ticket> selectSwitchUnConfirm(Long userId);

    //public List<Ticket> selectReceverList(Long userId);

    public void doDiscount(Long ticketId,Long userId) throws ParseException;

    public void acceptance(Long ticketId) throws ParseException;

    public List<TicketHistory> selectAcceptanceFailure(Long uid);

    public void mortgage(JSONObject json) throws ParseException;

    public void confirmMortgage(JSONObject json) throws ParseException;

    public List<Ticket> selectUnConfirmMortgageList(Long userId);
}
