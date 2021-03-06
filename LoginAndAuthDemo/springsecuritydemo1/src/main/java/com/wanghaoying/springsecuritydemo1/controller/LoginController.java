package com.wanghaoying.springsecuritydemo1.controller;

import com.wanghaoying.springsecuritydemo1.pojo.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class LoginController {
    @GetMapping("login")
    public String login(){
        return "/html/login/login";
    }

    @GetMapping("hello")
    public String hello(){
        return "hello world";
    }

    @GetMapping("index")
    public String index(){
        return "hello index";
    }

    @GetMapping("update")
//    @Secured({"ROLE_admin"})  // @Secured 可以传入一个角色数组，但是只能处理角色控制
//    @PreAuthorize("hasAuthority('admin')")  // @PreAuthorize 在进入方法之前做一次校验，
                                            // 既可以做基于角色的校验又可以做基于权限的校验
//    @PostAuthorize("hasAuthority('admin')")  // @PostAuthorize 在执行完方法返回之后，但在
                                // 返回给浏览器之前做权限校验，同可以可以支持基于权限和角色的校验
    public String update(){
        System.out.println("updating......");
        return "hello update";
    }

    @GetMapping("getAll")
//    @PostFilter("filterObject.username=='wanghao'") // 对方法的返回值进行过滤，
//                                        // 只返回username为wanghao的元素
//    @PreFilter("filterObject.username=='wanghao'")  // 对方法输入的参数进行过滤
//                                          // 只允许username为wanghao的请求进入方法
    public List<Users> getAll(@RequestBody List<Users> list){
        ArrayList<Users> users = new ArrayList<>();
        users.add(new Users("wanghao","",""));
        users.add(new Users("admin","",""));
        return users;
    }
}
