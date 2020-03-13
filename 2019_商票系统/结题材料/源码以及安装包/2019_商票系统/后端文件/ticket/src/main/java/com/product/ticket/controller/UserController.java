package com.product.ticket.controller;

import com.alibaba.fastjson.JSONObject;
import com.product.ticket.bean.Result;
import com.product.ticket.entity.User;
import com.product.ticket.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/ticket")
public class UserController {

    @Autowired
    UserServiceImpl usererService;

    @PostMapping("/login")
    public Result login(@RequestBody JSONObject json){
        Result<Object> result = new Result<>();

        String userName = json.getString("userName");
        String passwd = json.getString("passwd");
        User login = usererService.login(userName, passwd);
        if(login!=null && login.getPasswd()!=null){
            login.setPasswd(null);
            result.setSuccess(true);
            result.setMsg("登录成功");
            result.setData(login);
            System.out.println(login.getId());
        }else if(login.getPasswd()==null){
            result.setSuccess(false);
            result.setMsg("登录失败，密码错误");
        }else{
            result.setSuccess(false);
            result.setMsg("登录失败，请检查用户名");
        }
        return result;
    }


    @PostMapping("/register")
    public Result register(@RequestBody User user){
        Result<Object> result = new Result<>();

        try {
            User login = usererService.register(user);
            login.setPasswd(null);
            result.setSuccess(true);
            result.setMsg("注册成功");
            result.setData(login);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }

        return result;
    }

    @GetMapping("getBalance")
    public Result register(@RequestParam Long uid){
        Result<Object> result = new Result<>();

        try {
            String price =  usererService.getPrice(uid);
            result.setSuccess(true);
            result.setMsg("查询成功");
            result.setData(price);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }

        return result;
    }
}
