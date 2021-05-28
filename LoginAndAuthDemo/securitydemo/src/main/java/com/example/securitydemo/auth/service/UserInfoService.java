package com.example.securitydemo.auth.service;


import com.example.securitydemo.auth.pojo.UserInfo;

public interface UserInfoService  {

    UserInfo getUserByName(String username);

    UserInfo getUserById(String id);
}
