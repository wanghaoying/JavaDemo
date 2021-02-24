package com.redis.service.Impl;

import com.redis.service.RequestNumService;

public class RequestNumServiceImpl implements RequestNumService {

    public void service(Long num) {
        System.out.println("正在执行相应的服务...，当前剩余次数"+num);
    }
}
