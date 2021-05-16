package org.example.bean;

import org.example.aop.MathCalculator;
import org.example.config.AppConfig06;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AopTest {

    @Test
    public void aopTest01(){
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig06.class);

        MathCalculator bean = applicationContext.getBean(MathCalculator.class);
//        bean.div(1,1);
        bean.div(1,0);
    }
}
