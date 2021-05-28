package com.example.securitydemo.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.securitydemo.auth.pojo.UserInfo;
import org.springframework.stereotype.Repository;


@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
