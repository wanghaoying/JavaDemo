package com.wanghaoying.springsecuritydemo1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/login")
public class LoginController {

    @GetMapping("hello")
    public String hello(){
        return "hello world";
    }
}
