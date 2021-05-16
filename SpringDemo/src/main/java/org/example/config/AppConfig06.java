package org.example.config;


import org.aspectj.lang.annotation.Before;
import org.example.aop.LogAspects;
import org.example.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import sun.rmi.runtime.Log;

/**
 * AOP: [动态代理]
 *     指在程序在运行期间可以将某段代码动态的切入到另一个方法的指定位置，不需要
 * 将这段代码硬编码到对应的方法中就可以完成预期的功能
 *
 *      1、导入AOP模块，spring-aspects
 *      2、编写一个业务逻辑类，实现里面的业务逻辑方法
 *      3、编写一个切面类，实现切入的方法，主要有一下几种类型
 *          通知方法类型：
 *              1、前置通知 --> @Before
 *              2、后置通知 --> @After
 *              3、正常返回通知  --> @AfterReturning
 *              4、异常返回通知  --> @AfterThrowing
 *              5、环绕通知     --> @Around
 *      4、编写切入点表达式（定位需要被切入的类中的方法）
 *      5、将业务逻辑类 和 切面类交给Spring来进行管理
 *      6、告诉Spring那个类是切面类 @Aspect
 *      7、开启Spring的aspects-auto-proxy功能  @EnableAspectJAutoProxy
 */
@EnableAspectJAutoProxy
@Configuration
public class AppConfig06 {
    // 将业务逻辑类交给Spring管理
    @Bean
    public MathCalculator mathCalculator(){
        return new MathCalculator();
    }
    // 将切面类交给Spring管理
    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }

}
