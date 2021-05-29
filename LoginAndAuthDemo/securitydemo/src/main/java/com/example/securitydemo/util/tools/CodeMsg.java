package com.example.securitydemo.util.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeMsg {
    private int code;
    private String msg;

    // 通用状态码
    public static CodeMsg SUCCESS = new CodeMsg(200, "成功");

    // 用户模块 5002XX


}
