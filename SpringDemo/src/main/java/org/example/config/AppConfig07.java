package org.example.config;

import org.example.pojo.Car;
import org.example.service.CarService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CarService.class})
public class AppConfig07 {

//    @Bean("car01")
//    public Car car(){
//        return new Car(123,456);
//    }
}
