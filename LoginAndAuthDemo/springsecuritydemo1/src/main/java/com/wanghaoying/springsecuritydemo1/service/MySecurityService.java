package com.wanghaoying.springsecuritydemo1.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanghaoying.springsecuritydemo1.dao.MySecurityMapper;
import com.wanghaoying.springsecuritydemo1.pojo.Sys_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class MySecurityService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MySecurityMapper mySecurityMapper;

    // 这里存在一个bug，就是无论username输入什么，只要password正确，就可以实现认证成功
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 定义一个queryWrapper
        QueryWrapper<Sys_user> wrapper = new QueryWrapper<>();
        wrapper.eq("username",s);  // 按照用户名来进行匹配
        Sys_user sysuser = mySecurityMapper.selectOne(wrapper);

        if (sysuser == null){
            throw new UsernameNotFoundException("这个用户不存在！");
        }

        // 能找到这个用户,就创建一个user对象，然后交给security模块去进行匹配
        return new User(sysuser.getUsername(),
                passwordEncoder.encode(sysuser.getPassword()),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_sale"));
    }
}
