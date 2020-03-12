package com.product.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.product.ticket.entity.Message;
import com.product.ticket.mapper.MessageMapper;
import com.product.ticket.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;


    /**
     * 查询消息列表
     * @param uid
     * @return
     */
    @Override
    public List<Message> getMessageListByUid(Long uid) {
        List<Message> uid1 = messageMapper.selectList(new QueryWrapper<Message>().eq("uid", uid));
        return uid1;
    }
}
