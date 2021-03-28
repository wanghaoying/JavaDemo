package org.example.conditional;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WindowsCondition implements Condition {
    /**
     * @param context  application 上下文环境
     * * @param metadata   类或者方法上的注解信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 获取ioc容器的beanfactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        // 获取运行时环境
        Environment environment = context.getEnvironment();
        // 获取当前程序使用的类加载器
        ClassLoader classLoader = context.getClassLoader();
        // 获取beanDefinition的注册信息，registry中包含了已经注册进来
        // 的beanDefinition信息
        BeanDefinitionRegistry registry = context.getRegistry();

        String osName = environment.getProperty("os.name");
        if (osName.contains("Windows")){
            return true;
        }
        return false;
    }
}
