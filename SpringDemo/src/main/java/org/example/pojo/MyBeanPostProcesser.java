package org.example.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

// Bean的后置处理器，需要被Spring的ioc容器扫描，并作为一个组件加入
@Component
public class MyBeanPostProcesser implements BeanPostProcessor {
    /**
     * @param bean the new bean instance   已经调用完构造器方法生成的Bean实例，已经完成了属性值的初始化
     * @param beanName the name of the bean   这个实例的名字，也就是这个bean的id
     * @return  这里既可以返回参数传来的Bean，也可以对Bean进行包装之后在返回
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization------>>>>>"+ beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization------->>>>>"+beanName);
        return bean;
    }
}

