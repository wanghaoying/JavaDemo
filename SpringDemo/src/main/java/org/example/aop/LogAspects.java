package org.example.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * 切面通知方法的执行顺序是：before -> afterReturning or afterThrowing -> after
 *
 * 在环绕通知和 其他四种通知均存在的情况下，切面通知方法的执行顺序是：
 *      1、环绕前置通知
 *      2、Before通知
 *      3、AfterReturning or AfterThrowing  通知
 *      4、After通知
 *      5、环绕正常返回通知 or 环绕异常返回通知
 *      6、环绕后置通知
 */
@Aspect
public class LogAspects {
    @Pointcut("execution(* org.example.aop.MathCalculator.*(..))")
    public void pointCut(){ }

    // 前置通知，在切入点方法执行之前执行这段代码
    @Before("pointCut()")
    public void longStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println(""+ joinPoint.getSignature().getName()
                +" method starting, params is "+ Arrays.asList(args) +"");
    }

    // 后置通知，在切入点方法执行完毕之后执行这段代码
    @After("pointCut()")
    public void longEnd(JoinPoint joinPoint){
        System.out.println(""+ joinPoint.getSignature().getName()
                +" method ending");
    }

    // 正常返回通知，在切入点方法正常返回的时候执行这段代码
    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturn(JoinPoint joinPoint,Object result){
        System.out.println(""+ joinPoint.getSignature().getName()
                +" method returning, result is "+ result +"");
    }

    // 异常返回通知，在切入点方法异常返回的时候执行这段代码
    @AfterThrowing(value = "pointCut()",throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception){
        System.out.println(""+ joinPoint.getSignature().getName()
                +" method throwing, exception is "+exception+"");
    }

    // 环绕通知
    @Around("pointCut()")
    public void logAround(ProceedingJoinPoint joinPoint){
        // 这里执行环绕前置通知
        System.out.println("环绕前置通知...,参数列表为："+Arrays.asList(joinPoint.getArgs()));
        try {
            // 执行目标方法
            Object proceed = joinPoint.proceed();
            // 这里执行环绕正常返回通知
            System.out.println("环绕正常返回通知...,结果为:"+proceed);
        } catch (Throwable throwable) {
            // 这里执行环绕异常通知
            System.out.println("环绕异常返回通知...,异常原因为："+throwable.getMessage());
        }
        // 这里执行环绕后置通知
        System.out.println("环绕后置通知...");
    }

}
