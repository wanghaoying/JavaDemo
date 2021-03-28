package org.example.config;

import org.example.conditional.MacosCondition;
import org.example.conditional.WindowsCondition;
import org.example.controller.CarController;
import org.example.importTest.MyImportRegistry;
import org.example.importTest.MyImportSelector;
import org.example.pojo.Car;
import org.example.pojo.MyFactoryBean;
import org.example.pojo.Person;
import org.springframework.context.annotation.*;

@Configuration
//@Import({CarController.class, MyImportSelector.class, MyImportRegistry.class})
//@Conditional(WindowsCondition.class)
public class AppConfig02 {

    /**
     * 可以使用scope来设置Bean的作用范围
     *
     * scope 可以取一下四个值：
     *   SCOPE_PROTOTYPE ： 多实例  每个从ioc容器中获取这个实例，都会去新建一个bean实例，
     *   然后进行返回，如果不获取这个bean实例，那么ioc容器也就不会去加载他
     *
     * 	 SCOPE_SINGLETON ： 单实例  Bean的加载方式默认是单例的，ioc会在容器进行初始化的时候
     * 	 将这个bean加载到容器中，即不过你以后获不获取这个bean，我都会将他加载到容器中来
     *
     * 	 SCOPE_REQUEST  ： 每个request创建一个实例
     * 	 SCOPE_SESSION ： 每个session会话创建一个实例
     *
     * 	 懒加载： 作用于单例模式 @Lazy
     *
     * 	    单例模式中的bean，默认是在ioc容器进行初始化的时候创建并加入容器中，但是如果我们
     * 	    在整个声明周期内都没有去获取这个bean的时候，这就是一种资源的浪费，所以Spring就
     * 	    提供了一种 ** 延迟加载 ** 的模式，等到向容器中获取bean的时候，再去创建并初始化这个
     * 	    bean
     *
     */
    @Scope("singleton")
    @Lazy
    @Bean("dz")
    public Car car(){
        System.out.println("创建了一个Bean实例");
        return new Car(22,11);
    }


    /**
     * @Conditional 注解：有时候我们添加了组件注解的Bean，并不希望每次他都加进来
     * 而是希望他在满足某些条件的时候，再加入到ioc容器中，这里就需要使用我们的
     * @Conditional 注解了。 这个注解接受一个实现了Condition接口的实现类数组
     *
     * @Conditional 可以加在类上或者方法上
     *
     * 比如下面这两个操作，我们希望在我们的代码运行的操作系统是windows时，只将person01
     * 加进来，在是Macos时，只将person02加进来
     */
    @Bean("person01")
    @Conditional(WindowsCondition.class)
    public Person person01(){
        return new Person("person01",18);
    }

    @Bean("person02")
    @Conditional(MacosCondition.class)
    public Person person02(){
        return new Person("person02",20);
    }

    /**
     * 给ioc容器中添加组件，主要有以下几种形式：
     * 1、对于自定义的类，通过包扫描+注解(@Controller\@Service\@Repositry\@Component)
     *
     * 2、对于第三方包，我们不能直接在类上加注解，可以使用@Bean的方式，但这样需要写一个方法
     * 来返回这个新建的对象
     *
     * 3、Spring提供了一个快速导入其他类的模式 @Import --- > 只能加在类上
     *        1）、@Import 接受一个待加载类的数组，可以快速导入这些类,id是类的全类名
     *        2）、当需要加载较多类的时候，可能在@Import的数组中写这么多类比较复杂，
     *       因此Spring提供了一个ImportSelector接口，可以通过传入实现这个接口的类，
     *       避免在@Import中，添加较长的类
     *        3）、也可以去实现一个 **ImportBeanDefinitionRegistrar**
     *       接口的类，来手工的添加类
     *
     * 4、通过FactoryBean来实现导入，通过创建一个实现了FactoryBean接口的实现类，将这个
     * 实现类加入到ioc容器中，当我们下次获取这个id的时候，其实获取的是这个实现类的getObject
     * 方法返回的类。
     *    其实就是将这个生产Bean的工厂，也作为一个Bean加入到ioc容器中，这个工厂
     * 可以指定生产Bean的模式，单例或者prototype。一般来说，这个工厂Bean只生产一种类型
     * 的Bean
     *
     */

    @Bean
    public MyFactoryBean myFactoryBean(){
        return new MyFactoryBean();
    }

}
