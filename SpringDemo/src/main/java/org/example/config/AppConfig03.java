package org.example.config;

import org.example.pojo.Car;
import org.example.pojo.Dog;
import org.example.pojo.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("org.example.pojo")
public class AppConfig03 {

    /**
     * Bean的声明周期一般包括如下三个阶段：
     *    调用构造器创建bean -- bean的初始化 -- bean的销毁
     *
     * 这三个过程可以交给容器来管理，ioc容器可以在bean的初始化和销毁的时候调用他的初始化和
     * 销毁方法
     *
     * 调用构造器创建Bean：
     *          单实例： ioc初始化的时候，就会创建这个Bean
     *          多实例： ioc等用户真正的去获取这个Bean的时候，才会去创建
     * 初始化阶段：
     *          单实例： 调用Bean的构造器完成，并完成相应的赋值操作，调用初始化方法
     *          多实例： 同上
     * Bean的销毁时间：
     *          单实例： 在容器关闭的时候进行销毁
     *          多实例： 容器并不负责多实例Bean的销毁，他认为这个工作应该用户自己完成，在
     *        用户获取到多实例Bean的时候，这个Bean就不再被ioc容器所管理了
     */
    // 可以在@Bean注解处调用 bean的初始化和销毁方法
    @Scope("prototype")
    @Bean(value = "tomcat",initMethod = "init",destroyMethod = "destory")
    public Car car(){
        return new Car();
    }

    /**
     * 同时，Spring也提供了另外一种方法，来管理Bean的初始化和销毁方法。
     * 可以让我们的Bean实现 InitializingBean 和 DisposableBean 接口
     */
    @Scope("prototype")
    @Bean
    public Person person(){
        return  new Person();
    }

    /**
     * 也可以使用注解来标示Bean的初始化和销毁方法
     * @PostConstruct : 在Bean创建以及属性赋值完成之后调用，即构造器方法之后调用
     * @PreDestroy    : 在Bean销毁之前调用这个方法
     */
    @Scope("prototype")
    @Bean
    public Dog dog(){
        return  new Dog();
    }


    /**
     * Spring提供了一个BeanpostProcesser的方式，来在 初始化方法Init-method 之前和
     * 之后设置指定的方法执行
     *  postProcessBeforeInitialization: 在 init-method方法之前运行
     *  postProcessAfterInitialization：在init-method方法之后运行
     *
     *  整个流程如下：
     *  constructor--> postProcessBeforeInitialization --> init-method --->
     *  postProcessAfterInitialization
     *
     *  person constructor...
     *  postProcessBeforeInitialization------>>>>>张三
     *  person init...
     *  postProcessAfterInitialization------->>>>>张三
     *
     *  Spring可以识别上面的三种方式所指定的 init-method
     *---------------------------------------------------------------------
     * BeanPostProcessor的作用方式大概如下所示：
     *     创建Bean{
     *         //对这个实例的一些属性的初始化
     *         populateBean(beanName, mbd, instanceWrapper);
     *         //对这个Bean进行实例化
     * 		   exposedObject = initializeBean(beanName, exposedObject, mbd);{
     * 		        //对这个Bean执行Bean后置处理器中的before方法
     * 		        applyBeanPostProcessorsBeforeInitialization{
     * 		            // 采用的是遍历所有的processor，对Bean进行处理，只要有一个processor返回null，就结束遍历
     * 		            for (BeanPostProcessor processor : getBeanPostProcessors()) {
     * 			               Object current = processor.postProcessBeforeInitialization(result, beanName);
     * 			                if (current == null) {
     * 				                return result;
     *                        }
     * 			        result = current;
     * 			        }
     * 		        }
     * 		        invokeInitMethods
     * 		        applyBeanPostProcessorsAfterInitialization  // 和 Before采用类似的方式进行处理
     * 		    }
     *     }
     *
     *
     *     BeanPostProcessor 在Spring的底层中拥有大量的应用，比如如果我们想给一个bean中
     *  注入一个ApplacationContext对象，我们只要为这个对象实现 ** ApplicationContextAware **
     *  接口，并重写这个接口的setApplicationContext方法，将参数中的applcationContext
     *  赋值给我们的变量就可以了，这里面的主要原理就是，Spring中有一个
     *   ** ApplicationContextAwareProcessor ** 这也是一个BeanPostProcessor，会在InitMethod
     *   方法之前被遍历调用，并执行他的postProcessBeforeInitialization方法：
     *
     *     postProcessBeforeInitialization{
     *         if (!(bean instanceof EnvironmentAware || bean instanceof EmbeddedValueResolverAware ||
     * 				bean instanceof ResourceLoaderAware || bean instanceof ApplicationEventPublisherAware ||
     * 				bean instanceof MessageSourceAware || bean instanceof ApplicationContextAware)){
     * 			return bean;
     *         }    // 如果传入的这个Bean实现了上面的这些接口，执行下面的操作，否则直接返回
     *
     *         ...  // 前置操作
     *         invokeAwareInterfaces{
     *              ... // 对属于其他接口的一些操作
     * 		        if (bean instanceof ApplicationContextAware) {
     * 			        ((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
     *              }
     *         }
     *         ... // 后置操作
     *     }
     *
     *      类似的还有针对Bean中的@Autowire注解的** AutowiredAnnotationBeanPostProcessor **
     *   可以实现对标注了@Autowire注解的属性的自动注入值
     *      同样的还有类似的针对Bean的生命周期注解@PostConstruct 和 @PreDestory 的BeanPostProcessor
     *
     *    总之，Spring使用BeanPostProcessor，帮助我们实现了包括但不限于以下的功能：{
     *      bean的属性赋值，自动装配(@Autowire、applicatonContext)，数据的校验，
     *      bean的生命周期方法的执行，@Async等等功能
     *    }
     */
    @Bean(value = "张三")
    public Person person01(){
        return new Person();
    }

}
