package org.example;

import org.example.config.AppConfig01;
import org.example.config.AppConfig02;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        // 基于注解annotation的，使用一个配置类来代替传统的配置文件
        AnnotationConfigApplicationContext applicationContext
                  = new AnnotationConfigApplicationContext(AppConfig02.class);
        System.out.println("ioc容器加载完成");

    }
}
