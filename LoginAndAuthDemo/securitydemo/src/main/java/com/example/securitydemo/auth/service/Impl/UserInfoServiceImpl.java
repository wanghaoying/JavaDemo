package com.example.securitydemo.auth.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.securitydemo.auth.dao.UserInfoMapper;
import com.example.securitydemo.auth.pojo.UserInfo;
import com.example.securitydemo.auth.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserByName(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        return userInfo;
    }

    @Override
    public UserInfo getUserById(String id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        return userInfo;
    }
}
