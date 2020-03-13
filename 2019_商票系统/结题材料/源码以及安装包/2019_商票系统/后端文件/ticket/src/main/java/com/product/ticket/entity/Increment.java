package com.product.ticket.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("tkt_increment")
public class Increment {

    private Long num;

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }
}
