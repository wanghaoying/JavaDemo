package org.example.config;

import org.example.pojo.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig07 {

    @Bean
    public Car car(){
        return new Car();
    }
}
