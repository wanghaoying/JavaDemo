package com.example.securitydemo.auth.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserDetail implements UserDetails {
    // 这两个属性，我们会在UserService里面将它包装进去
    private UserInfo userInfo;
    private List<RoleInfo> roleInfos;

    private Collection<? extends GrantedAuthority> grantedAuthorities;
    private List<String> roles;


    // 主要是根据roleInfo中的信息，解析出这个用户所具有的角色，我们是基于角色的权限控制
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (grantedAuthorities != null){
            return grantedAuthorities;
        }

        // 根据用户的roleInfo 获取用户的权限
        ArrayList<String> authorities = new ArrayList<>();
        ArrayList<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        roleInfos.forEach(roleInfo -> {
            authorities.add(roleInfo.getRoleCode());
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roleInfo.getRoleCode()));
        });

        this.roles = authorities;
        this.grantedAuthorities = grantedAuthorities;

        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userInfo.getUsername();
    }

    // 这个账户是否没有过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 这个账户是否没有被锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 凭证是否没有过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账号是否启用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
