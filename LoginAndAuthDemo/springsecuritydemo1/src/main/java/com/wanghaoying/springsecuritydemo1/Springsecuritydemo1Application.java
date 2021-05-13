package com.wanghaoying.springsecuritydemo1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wanghaoying.springsecuritydemo1.dao")
public class Springsecuritydemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Springsecuritydemo1Application.class, args);
    }

}
