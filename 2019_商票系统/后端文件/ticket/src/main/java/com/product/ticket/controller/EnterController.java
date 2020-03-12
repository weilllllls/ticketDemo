package com.product.ticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EnterController {
    @RequestMapping("/login")
    public String index(){
        return "login";
    }

    @RequestMapping("/info")
    public String info(){
        return "info";
    }
}
