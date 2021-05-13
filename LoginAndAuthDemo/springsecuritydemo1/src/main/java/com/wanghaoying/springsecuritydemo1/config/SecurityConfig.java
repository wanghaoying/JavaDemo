package com.wanghaoying.springsecuritydemo1.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
// 下面这个注解可以开启基于注解的角色权限管理
// securedEnabled=true   可以开启 @Secured({"ROLE_admin"})

@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    // 实现remember-me的功能，存在一个问题，如果用户不手动退出，那么这些token记录一直
    // 存储在数据库中
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 设置启动时自动建表 因为他每次都是createTable 而不是 create table if not exits
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
//        String password = bcpe.encode("123456");
//        auth.inMemoryAuthentication().passwordEncoder(bcpe).withUser("admin")
//                .password(password).roles("admin");

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 这是用户退出之后的跳转页面
        http.logout().logoutUrl("/logout")
                .logoutSuccessUrl("/html/login/login.html").permitAll();

        // 设置异常页面，比如发生403异常时跳转的页面
        http.exceptionHandling().accessDeniedPage("/html/error/403.html");

        // 自定义自己编写的登录页面
        http.formLogin()
                // 编写的登录界面
                .loginPage("/html/login/login.html")
                // 后端接受login处理请求的路径
                .loginProcessingUrl("/user/login")
                // 登陆成功之后跳转到的路径
                .defaultSuccessUrl("/html/login/seccess.html",true).permitAll()
                .and().authorizeRequests()
                // 设置哪些路径可以不用登录验证
                .antMatchers("/","/user/login/hello","/user/login").permitAll()
                // 给资源添加访问权限，只有用户拥有访问权限的情况下，才可以访问这个资源
                // 1、hasAuthority("XXX"): 代表只有拥有XXX权限的用户才可以访问这个资源
                // 但是如果想设置这个资源可以有多个权限可以访问，使用下面的方式是不行的
                // 想通过hasAuthority("XXX,YYY") 来实现XXX 和 YYY权限都可以访问的功能是不可以的
//                .antMatchers("/user/login/index").hasAuthority("admin,manager")

                // 如果想实现这个资源允许多个权限访问的功能，可以通过下面的方法来实现
                // 2、hasAnyAuthority("XXX,YYY") 对于拥有XXX 和 YYY权限都可以访问这个资源
//                .antMatchers("/user/login/index").hasAnyAuthority("admin,manager")

                // 另外还可以，对资源设置基于角色的访问控制
                // 3、hasRole("XXX") 只有角色为XXX的用户才可以访问这个资源
                //     但是需要注意一个问题，在userDetailsService 中new一个user的时候，需要
                //   将其角色写为 ROLE_XXX的格式
//                .antMatchers("/user/login/index").hasRole("sale")
                // 4、与hasAnyAuthority类似
                .antMatchers("/user/login/index").hasAnyRole("sale,customer")
                .anyRequest().authenticated()
                // 设置remember-me功能
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
