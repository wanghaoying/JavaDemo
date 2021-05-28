package com.example.securitydemo.auth.service.Impl;

import com.example.securitydemo.auth.dao.RoleInfoMapper;
import com.example.securitydemo.auth.pojo.RoleInfo;
import com.example.securitydemo.auth.service.RoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleInfoServiceImpl implements RoleInfoService {
    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Override
    public List<RoleInfo> getRoleInfoListByUserId(String userId) {
        if (userId == null || userId.length() == 0){
            return new ArrayList<>();
        }

        return roleInfoMapper.getRoleInfoByUserId(userId);
    }
}
