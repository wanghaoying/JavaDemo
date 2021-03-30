package org.example.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Person implements InitializingBean, DisposableBean,
                ApplicationContextAware {

    private ApplicationContext applicationContext;
    @Value("张三")
    private String name;
    @Value("#{20-1}")   // SpEL表达式
    private int age;
    @Value("${person.nickname}")  //@Value("${os.name}")
    private String nickname;

    public Person() {
        System.out.println("person constructor...");
    }

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age, String nickname){
        this.name = name;
        this.age = age;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickname='" + nickname + '\'' +
                '}';
    }

    @Override
    public void destroy() throws Exception {
//        System.out.println("person destory...");
    }

    // 这个方法会在Bean创建完成，并完成属性的赋值之后执行
    @Override
    public void afterPropertiesSet() throws Exception {
//        System.out.println("person init...");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
