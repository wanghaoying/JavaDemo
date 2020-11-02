package com.Thread.Join;

public class JoinTest implements Runnable{

    /**
     * join方法只会阻塞当前调用他的线程，如果有其他线程在join之前start
     * 那么会交替运行
     */
    @Override
    public void run() {
//        if("Vip".equals(Thread.currentThread().getName())){
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+"高级线程正在运行..."+i);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JoinTest joinTest = new JoinTest();
        Thread thread = new Thread(joinTest,"Vip");
        thread.start();

        new Thread(joinTest,"last").start();

        for (int i = 0; i < 200; i++) {
            System.out.println("main线程正在执行-->"+i);
            if(i == 100){
                thread.join();
            }
        }
    }
}
