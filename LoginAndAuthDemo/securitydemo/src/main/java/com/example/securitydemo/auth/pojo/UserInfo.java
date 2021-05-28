package com.example.securitydemo.auth.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("user_info")
@Data
public class UserInfo {
    @TableId("id")
    private String id;

    private String username;

    private String password;

    private Integer activeStatus;

    private LocalDateTime createTime;

}
