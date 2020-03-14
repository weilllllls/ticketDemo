package com.product.ticket.controller;

import com.product.ticket.bean.Result;
import com.product.ticket.entity.Message;
import com.product.ticket.service.impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/ticket")
public class MessageController {

    @Autowired
    MessageServiceImpl messageService;


    @GetMapping("getMessageListByUid")
    public Result getMessageListByUid(@RequestParam Long uid){
        Result result = new Result();
        result.setSuccess(true);
        result.setMsg("查询成功");
        List<Message> messageListByUid = messageService.getMessageListByUid(uid);
        result.setData(messageListByUid);
        return result;
    }
}