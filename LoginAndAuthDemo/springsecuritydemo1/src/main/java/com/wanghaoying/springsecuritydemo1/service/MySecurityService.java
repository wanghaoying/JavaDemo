package com.wanghaoying.springsecuritydemo1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class MySecurityService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;
    // 这里存在一个bug，就是无论username输入什么，只要password正确，就可以实现认证成功
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> auths = AuthorityUtils
                .commaSeparatedStringToAuthorityList("admin");
        return new User("admin",
                passwordEncoder.encode("123456"), auths);
    }
}
