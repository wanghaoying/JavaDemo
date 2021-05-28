package com.example.securitydemo.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.securitydemo.auth.pojo.RoleInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleInfoMapper extends BaseMapper<RoleInfo> {
    List<RoleInfo> getRoleInfoByUserId(String userId);
}
