package com.product.ticket.service;

import com.product.ticket.entity.User;

public interface UserService {

    //登录
    public User login(String userName,String password);

    //注册
    public User register(User user);

    public String getPrice(Long uid);
}
