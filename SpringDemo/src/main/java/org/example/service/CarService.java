package org.example.service;

import org.example.dao.CarDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

@Service
public class CarService {

//    @Qualifier("carDao3")
//    @Autowired(required = false)
//    @Resource
//    @Inject
    CarDao carDao;

//    @Autowired
    ApplicationContext applicationContext;

    private int age;

    public CarService() {
    }

    //    @Autowired
    public CarService(@Autowired CarDao carDao) {
        this.carDao = carDao;
    }

    public CarDao getCarDao() {
        return carDao;
    }

//    @Autowired
    public void setCarDao(@Autowired CarDao carDao) {
        this.carDao = carDao;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "CarService{" +
                "carDao=" + carDao +
                ", applicationContext=" + applicationContext +
                ", age=" + age +
                '}';
    }
}
