package com.example.securitydemo.auth.service.Impl;

import com.example.securitydemo.auth.pojo.RoleInfo;
import com.example.securitydemo.auth.pojo.UserDetail;
import com.example.securitydemo.auth.pojo.UserInfo;
import com.example.securitydemo.auth.service.RoleInfoService;
import com.example.securitydemo.auth.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

// 这里必须要重新定义Bean的id吗？ 待测试
@Service("userDetailsService")
public class CustomUserService implements UserDetailsService {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RoleInfoService roleInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // 根据用户名来获取用户的相关信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoService.getUserByName(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userInfo.getUsername(),userInfo.getPassword());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // 如果用户名密码认证没有问题，对userDetail进行包装
        UserDetail userDetail = new UserDetail();
        userDetail.setUserInfo(userInfo);
        // 获取该用户的权限信息
        List<RoleInfo> roleInfos = roleInfoService.getRoleInfoListByUserId(userInfo.getId());
        userDetail.setRoleInfos(roleInfos);

        return userDetail;
    }
}
