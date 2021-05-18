package org.example.IocTheory;

import org.example.pojo.Car;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
