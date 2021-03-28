package org.example.pojo;

public class Car {
    private int carId;
    private int age;

    public Car() {
        System.out.println("Car constructor...");
    }

    public Car(int carId, int age) {
        this.carId = carId;
        this.age = age;
    }

    public void init(){
        System.out.println("Car init ...");
    }

    public void destory(){
        System.out.println("Car destory...");
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", age=" + age +
                '}';
    }
}
