package org.example.IocTheory;

import org.example.config.AppConfig07;
import org.example.pojo.Car;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 基于Annotation的Spring IOC启动过程
 */
public class IocDemo02 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfig07.class);
        applicationContext.refresh();

        Car bean = applicationContext.getBean(Car.class);
        System.out.println(bean.getCarId());
    }
}
