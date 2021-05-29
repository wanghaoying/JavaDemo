package com.example.securitydemo.auth.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.securitydemo.auth.dao.PermissionInfoMapper;
import com.example.securitydemo.auth.dao.RoleInfoMapper;
import com.example.securitydemo.auth.pojo.PermissionInfo;
import com.example.securitydemo.auth.pojo.RoleInfo;
import com.example.securitydemo.auth.service.PermissionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionInfoServiceImpl implements PermissionInfoService {
    @Autowired
    private PermissionInfoMapper permissionInfoMapper;

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    /**
     * 根据uri来获取这个uri所对应的权限信息
     */
    @Override
    public PermissionInfo getPermissionInfoByUri(String uri) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("permission_uri",uri);
        PermissionInfo permissionInfo = permissionInfoMapper.selectOne(queryWrapper);
        return permissionInfo;
    }

    /**
     * 根据permission_id 来获取这个permission所对应的角色信息
     */
    @Override
    public List<RoleInfo> getRoleInfoByPermissionId(String id) {
        List<RoleInfo> roleInfoByPermissionId = roleInfoMapper.getRoleInfoByPermissionId(id);
        return roleInfoByPermissionId;
    }
}
