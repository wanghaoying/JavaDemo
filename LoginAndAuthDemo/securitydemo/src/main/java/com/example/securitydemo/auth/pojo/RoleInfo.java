package com.example.securitydemo.auth.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;


@TableName("role_info")
@Data
public class RoleInfo {
    @TableId("id")
    private String id;

    private String roleName;

    private String roleCode;

    private String roleRemark;

    private Integer activeStatus;

    private LocalDateTime createTime;
}
