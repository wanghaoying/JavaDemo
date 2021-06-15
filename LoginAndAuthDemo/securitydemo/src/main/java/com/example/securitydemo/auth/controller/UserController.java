package com.example.securitydemo.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.securitydemo.auth.pojo.user.AuthLoginInfo;
import com.example.securitydemo.auth.service.UserService;
import com.example.securitydemo.util.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * 主要处理和用户相关的逻辑
 * @author W.H
 */
@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登陆操作
     * 1、参数验证
     * 2、
     *
     * @param authLoginInfo
     */
    @PostMapping("/auth/login")
    public Result userLogin(@RequestBody @Valid AuthLoginInfo authLoginInfo){
        // 1、参数校验，通过在包装authLoginInfo的时候进行验证

        // 2、调用UserService的userLogin方法，来进行登陆

        // 3、对结果进行封装返回
        return Result.success();
    }
}
