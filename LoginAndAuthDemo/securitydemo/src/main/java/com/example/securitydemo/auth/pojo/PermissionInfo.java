package com.example.securitydemo.auth.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;


@TableName("permission_info")
@Data
public class PermissionInfo {
    @TableId("id")
    private String id;

    private String permissionName;

    private String permissionUri;

    private String permissionMethod;

    private Integer activeStatus;

    private LocalDateTime createTime;
}
