package com.example.securitydemo.auth.service;

import com.example.securitydemo.SecuritydemoApplication;
import com.example.securitydemo.auth.pojo.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecuritydemoApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void getUserByNameTest(){
        UserInfo user = userInfoService.getUserByName("user");
        assert user != null;
    }
}
