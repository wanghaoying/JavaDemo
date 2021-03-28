package org.example.config;

import org.example.beanFilter.ComponentScanFilter;
import org.example.pojo.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// 告诉Spring这是一个配置类
@Configuration
// 同样也可以和配置文件时一样，配置包扫描 配置include-filter 和 exclue-filter
// 同样的可以和xml配置文件一样，配置过滤规则
// 同时也可以使用自定义的包扫描过滤规则来进行扫描
//@ComponentScan(basePackages = "org.example", includeFilters = {
//        @ComponentScan.Filter(classes = Controller.class,type = FilterType.ANNOTATION)
//}, useDefaultFilters = false)
//@ComponentScan(value = "org.example", excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Service.class})
//})
@ComponentScan(value = "org.example", includeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM,classes = {ComponentScanFilter.class})
},useDefaultFilters = false)
public class AppConfig01 {

    // 使用Bean注解修饰的类不能是private方法的，返回类型就是Bean的类型
    // 默认方法名是Bean名字，但是可以通过name属性，来给这个Bean来起一个别名
    @Bean(name = "baoma")
    public Car car(){
        return new Car(22,11);
    }

}
