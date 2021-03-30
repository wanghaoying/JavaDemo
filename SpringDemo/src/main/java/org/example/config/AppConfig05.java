package org.example.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 *  有时候我们需要根据运行时环境的不同，来决定将哪些配置或者组件装入ioc容器中，Spring提供了一个
 * @Profile 注解，可以帮助我们实现这个功能
 *
 * @Profile 注解可以标注在类上，也可以标注在方法上
 *
 * 下面以配置不同的数据库连接池为例进行演示
 */
@Profile(value = {"test","dev","prod"})
@PropertySource("classpath:/iocTest.properties")
@Configuration
public class AppConfig05 implements EmbeddedValueResolverAware {

    // 这里展示了三种获取配置文件中的值的方式
    @Value("${db.user}")
    private String user;

    private String driverClass;

    @Profile("test")
    @Bean
    public DataSource dataSourceTest(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql//localhost:3306/test");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    @Profile("dev")
    @Bean
    public DataSource dataSourceDev(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql//localhost:3306/springboot");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    @Profile("prod")
    @Bean
    public DataSource dataSourceProd(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql//localhost:3306/springboot");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    // 可以获取字符串解析器，来对${}中的内容进行解析
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        driverClass = resolver.resolveStringValue("${db.driver}");
    }
}
