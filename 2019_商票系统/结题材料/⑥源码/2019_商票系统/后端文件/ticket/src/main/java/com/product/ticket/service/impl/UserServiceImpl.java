package com.product.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.product.ticket.entity.User;
import com.product.ticket.mapper.UserMapper;
import com.product.ticket.service.UserService;
import com.product.ticket.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    //登录
    @Override
    public User login(String userName, String password) {
        String md5 = MD5Utils.toMD5(password);
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", userName).eq("passwd", md5));
        if(user==null){
            User user1 = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", userName));
            user1.setPasswd(null);
            return user1;
        }else{
            return user;
        }
    }

    //注册
    @Override
    public User register(User user){
        User name = userMapper.selectOne(new QueryWrapper<User>().eq("name", user.getName()));
        User name1 = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", user.getUserName()));

        if(name==null&& name1==null){
            String s = MD5Utils.toMD5(user.getPasswd());
            user.setPasswd(s);
            user.setBalance("0");
            user.setCredit(100);
            userMapper.insert(user);
        }else{
            throw new RuntimeException("注册失败，已有该用户名和全称");
        }
        return user;
    }

    /**
     * 获取用户余额
     * @param uid
     * @return
     */
    @Override
    public String getPrice(Long uid) {
        User id = userMapper.selectOne(new QueryWrapper<User>().eq("id", uid));
        return id.getBalance();
    }
}
