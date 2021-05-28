package com.example.securitydemo.auth.service;

import com.example.securitydemo.auth.pojo.RoleInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceTest {

    @Autowired
    private RoleInfoService roleInfoService;

    @Test
    public void getRoleInfoListByUserIdTest(){
        List<RoleInfo> roleInfoListByUserId = roleInfoService.getRoleInfoListByUserId("1");
        assert roleInfoListByUserId != null;
    }
}
