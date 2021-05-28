package com.example.securitydemo.auth.service;

import com.example.securitydemo.auth.pojo.RoleInfo;

import java.util.List;

public interface RoleInfoService {

    List<RoleInfo> getRoleInfoListByUserId(String userId);
}
