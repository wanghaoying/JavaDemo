package org.example.bean;

import org.example.config.AppConfig01;
import org.example.config.AppConfig02;
import org.example.config.AppConfig03;
import org.example.pojo.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class IocTest {


    @Test
    public void test03(){
        // 基于注解annotation的 context环境，使用一个配置类来代替传统的配置文件
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig03.class);
        System.out.println("IOC 容器初始化完成");

        applicationContext.close();
    }

    @Test
    public void test02(){
        // 基于注解annotation的 context环境，使用一个配置类来代替传统的配置文件
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig02.class);

        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        Object myFactoryBean = applicationContext.getBean("myFactoryBean");
        Object myFactoryBean1 = applicationContext.getBean("myFactoryBean");
        System.out.println("myFactoryBean的类型是："+ myFactoryBean.getClass().getName());
        System.out.println(myFactoryBean == myFactoryBean1);
    }

    @Test
    public void test01(){
        // 基于注解annotation的 context环境，使用一个配置类来代替传统的配置文件
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig01.class);

        // 获取ioc中，所有类型为person的bean, 就算这个bean被@Lazy注解修饰，这个对象虽然在
        // 初始化ioc容器的时候不会创建，但是在这里获取这里类型的bean的时候，依然会进行创建
        Map<String, Person> beans = applicationContext.getBeansOfType(Person.class);
        System.out.println(beans);

        // 可以通过applicationContext来获取application的运行环境等信息
//        Environment environment = applicationContext.getEnvironment();
//        String property = environment.getProperty("os.name");
//        System.out.println(property);
    }
}
