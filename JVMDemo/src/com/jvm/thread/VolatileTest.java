package com.jvm.thread;

public class VolatileTest {

    public static void main(String[] args) {
        A a = new A();
        a.start();

        while(true) {
            if(a.isFlag()) {
                    System.out.println("A 当中的flag属性已经变成true了");
            }
        }
    }
}

class A extends Thread{
    private volatile boolean flag = false;

    public boolean isFlag(){
        return flag;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;

        System.out.println("我现在已经变为true了");
    }
}
