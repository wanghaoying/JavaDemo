package com.wanghaoying.springsecuritydemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class CsrfController {

    @GetMapping("/userlogin")
    public String login(){
        return "/html/login/login.html";
    }

    @GetMapping("/update")
    public String test(Model model){
        return "csrfTest";
    }

    @RequestMapping("/show")
    public String show(){
        return "csrfShow";
    }
}
