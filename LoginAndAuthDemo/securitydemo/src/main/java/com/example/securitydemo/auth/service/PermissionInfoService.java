package com.example.securitydemo.auth.service;

import com.example.securitydemo.auth.pojo.PermissionInfo;
import com.example.securitydemo.auth.pojo.RoleInfo;

import java.util.List;

public interface PermissionInfoService {

    PermissionInfo getPermissionInfoByUri(String uri);

    List<RoleInfo> getRoleInfoByPermissionId(String id);
}
