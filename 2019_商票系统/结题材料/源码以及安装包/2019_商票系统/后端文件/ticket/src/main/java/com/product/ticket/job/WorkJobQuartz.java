package com.product.ticket.job;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.product.ticket.entity.User;
import com.product.ticket.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Configurable
@EnableScheduling
public class WorkJobQuartz {

    @Autowired
    UserMapper userMapper;


    /**
     * 每晚12点定时任务
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void reportByCron() {

    }

}