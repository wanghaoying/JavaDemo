package org.example.pojo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Dog {
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
}
