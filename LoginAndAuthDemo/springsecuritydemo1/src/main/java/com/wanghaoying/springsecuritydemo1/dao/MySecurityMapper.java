package com.wanghaoying.springsecuritydemo1.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanghaoying.springsecuritydemo1.pojo.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface MySecurityMapper extends BaseMapper<Users> {

}
