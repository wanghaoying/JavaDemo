package com.example.securitydemo.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.securitydemo.auth.pojo.RoleInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleInfoMapper extends BaseMapper<RoleInfo> {
    // 根绝userId来获取这个user对应的role信息
    List<RoleInfo> getRoleInfoByUserId(String userId);

    // 根绝permissionId来获取这个permission对应的role信息
    List<RoleInfo> getRoleInfoByPermissionId(String permissionId);
}
