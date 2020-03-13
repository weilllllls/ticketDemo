package com.product.ticket.service;

import com.product.ticket.entity.Message;

import java.util.List;

public interface MessageService {


    public List<Message> getMessageListByUid(Long uid);
}
