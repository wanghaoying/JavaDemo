package org.example.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog  implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {
    private String name;
    private int age;


    public Dog() {
        System.out.println("dog constructor...");
    }

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
    // 在构造器方法调用之后调用
    @PostConstruct
    public void init(){
        System.out.println("dog init...");
    }
    // 在Bean销毁之前调用
    @PreDestroy
    public void destory(){
        System.out.println("dog destory...");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    // 这个是看 ioc中给这个Bean起的名字，即id
    @Override
    public void setBeanName(String name) {
        System.out.println("这个Bean的name是：---"+name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("这个Bean中传过来的ApplicationContext是：----"+applicationContext);
    }


    // 获取字符串值的编码解析器，可以解析 ${} #{}等内容
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        System.out.println(resolver.resolveStringValue("你好：${person.nickname}, 我是 #{20*18}"));
    }
}