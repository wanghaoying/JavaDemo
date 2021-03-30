package org.example.config;


// Value properties 属性赋值
// Autowire         自动装配
import org.example.dao.CarDao;
import org.example.pojo.Person;
import org.springframework.context.annotation.*;

// 可以通过@PropertySource注解来加载外部配置文件，这些外部配置文件中内容，被放到了Environment中
@PropertySource(value = {"classpath:iocTest.properties"})
@ComponentScan(value = {"org.example.service","org.example.dao","org.example.pojo"})
@Configuration
public class AppConfig04 {


    /**
     * 我们可以在类似配置文件中那样，通过@Value注解，来给Bean的属性指定，初始值。@Value 注解的
     * 允许赋值的属性包括以下几种：
     *      1、 基本数值 （数字，字符串，boolean等）
     *      2、 SpEl表达式 （通过#{}的形式)
     *      3、 配置文件中的内容   （通过${}的形式，主要是通过获取 Environment中的property属性）
     *    可以通过@PropertySource注解来加载外部配置文件
     *
     * 属性赋值-相当于-给Bean实例化之后再赋一个初始值
     *
     */
//    @Bean
    public Person person(){
        return new Person();
    }


    /**
     * 上面的是为Bean的属性附上默认值，一般是提前知道的数值，或者配置的内容。但是如果想为我们的Bean
     * 中的某个属性装配上某个在ioc容器中的Bean，我们可以使用下面的方法来进行装配：
     *   自动装配主要依赖于Spring提供的依赖注入功能(DI)
     *   1、 @Autowired 注解 --- 只要ioc容器中有一个这个类型的Bean，如果装配失败，就报错
     *       1）、默认按照类型来进行赋值，如果相同类型的Bean有多个，就将属性名作为id 在
     *     applicationContext中进行查找
     *       2）、@Qualifier: 如果有多个相同类型的Bean，也可以通过 @Qualifier 注解来指定id值，
     *     将指定Id的Bean，注入到我们的属性中
     *       3）、@AutoWired注解，默认是必须为我们的属性装配上一个Bean的，如果ioc容器中，没有这个
     *     Bean，那么就会报错，可以通过设置  @Autowired(required = false) ，当ioc容器中没有
     *     指定Bean的时候，就不赋值了
     *       4）、@Primary：当ioc容器中有多个相同类型的Bean的时候，可以通过在某个Bean上标注@Primary
     *     来提高这个Bean的优先级，对于没有指定注入哪个Bean的属性，就默认注入这个，@Qualifier不受影响
     *       5）、AutoWired存在一个问题：如果在属性上标注了@Autowired(required = false)，但是
     *     此时ioc容器中，有这个类型的Bean，但是属性名与这个Bean的id不相等，一样会发生注入错误。
     *
     *  2、@Resource注解 和 @Inject注解 （这两个注解都是Java规范定义的）
     *      @Resource：
     *            也可以实现依赖注入功能，但是不支持Spring提供的@Primary功能 和 @Autowired(required = false)功能
     *      @Inject：
     *            也可以实现依赖注入功能，支持Spring提供的@Primary功能,不支持@Autowired(required = false)功能
     *
     *  依赖注入的注解大致有三个：Spring提供的 @Autowired 和 Java规范定义的@Resource 和 @Inject
     *
     *  这些依赖注入功能的实现，主要是依靠  AutowiredAnnotationBeanPostProcessor 实现的
     *
     *  3、@Autowired 不仅可以标注在属性上，还可以标注在普通方法上、构造器上、参数上：
     *       普通方法上： 被Autowired标注的普通方法，参数能被ioc自动注入进去，但是存在一个问题，
     *    方法参数中的所有参数都需要能被注入，否则就会报错
     *       构造器上： Autowired 也可以标注在构造器上，参数也能会ioc自动注入进去，但是同样存在一个问题，
     *    方法参数中的所有参数都需要能被注入，否则就会报错
     *       参数上： 可以将执行类型的Bean注入到这个参数上,只有这个方法被调用的时候，这个参数才会起作用
     *    被注入
     *
     *       对于标注在方法上的注解，其参数只能包含能被ioc注入的内容，否则将会报错
     *    对于Spring会自动调用的方法，可以不标注 @Autowired，同样可以实现自动注入的效果
     *
     *  4、自定义组件中如果需要注入Spring底层的一些组件，例如ApplicationContext BeanFactory等等
     *  注入到我们的自定义的组件中，可以让我们的组件实现一个xxxAware接口：
     *      例如 ：ApplicationContextAware、BeanNameAware、EmbeddedValueResolverAware
     *
     *      这些 xxxAware工作的原理就是通过一个 xxxAwareProcess的后置处理器来进行工作，类似
     *      ApplicationContextAware ——————>> ApplicationContextAwareProcessor
     *
     *      当然，如果直接使用AutoWired也是可以将底层组件注入进来
     */
    @Primary()
    @Bean("carDao2")
    public CarDao carDao(){
        CarDao carDao = new CarDao();
        carDao.setLable(2);
        return carDao;
    }

}
