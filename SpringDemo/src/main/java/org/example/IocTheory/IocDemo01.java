package org.example.IocTheory;

import org.example.pojo.Car;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 基于配置文件的Spring IOC的启动过程，主要关注点在解析xml文件，获得对应的BeanDefinition
 * 然后将BeanDefinition注册到BeanDefinitionRegisty中
 */
public class IocDemo01 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("ioc.xml");

//        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName);
//        }

//        Car bean = applicationContext.getBean(Car.class);
//        System.out.println(bean);

    }
}
