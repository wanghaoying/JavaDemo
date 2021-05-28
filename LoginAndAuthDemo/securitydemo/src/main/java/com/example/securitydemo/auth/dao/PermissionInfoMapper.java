package com.example.securitydemo.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.securitydemo.auth.pojo.PermissionInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionInfoMapper extends BaseMapper<PermissionInfo> {

    // 获取每个权限（即目录）对应的所有的role列表
}
