package org.example.importTest;

import org.example.dao.CarDao;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportRegistry implements ImportBeanDefinitionRegistrar {
    /**
     * importingClassMetadata：包含了被@Import修饰的类的所有注解信息
     * registry： 包含了所有已经注册到registry的BeanDefinition的注册管理器
     *
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        boolean definition = registry.containsBeanDefinition(
                "org.example.controller.CarController");
        boolean definition1 = registry.containsBeanDefinition(
                "org.example.service.CarService");

        if (definition && definition1){
            // 定义一个CarDao类的 BeanDefinition
            BeanDefinition beanDefinition = new RootBeanDefinition(CarDao.class);
            beanDefinition.setScope("prototype");
            // 将这个BeanDefinition注册到registry中
            registry.registerBeanDefinition("carDao", beanDefinition);
        }
    }
}
