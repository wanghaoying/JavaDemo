package org.example.pojo;

import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<Person> {
    // 生产Bean的方法
    @Override
    public Person getObject() throws Exception {
        return new Person("张三",20);
    }

    // 获取生产的Bean的类型
    @Override
    public Class<?> getObjectType() {
        return null;
    }

    // 生产的Bean是否是单实例的,true代表单实例，false代表多实例
    @Override
    public boolean isSingleton() {
        return true;
    }
}
