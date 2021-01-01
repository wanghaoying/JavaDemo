package com.Thread.base;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
//        new Thread(new TimeWaiting (), "TimeWaitingThread").start();
//        new Thread(new Waiting(), "WaitingThread").start();
//        // 使用两个Blocked线程，一个获取锁成功，另一个被阻塞
//        new Thread(new Blocked(), "BlockedThread-1").start();
//        new Thread(new Blocked(), "BlockedThread-2").start();
        Thread a = new Thread(() -> {
            while (true){
                System.out.println("running...");
            }
        });
        a.start();
        Thread.interrupted();
        a.join();

        System.out.println("main thread fnished");
    }
    // 该线程不断地进行睡眠
    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100*100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // 该线程在Waiting.class实例上等待
    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    // 该线程在Blocked.class实例上加锁后，不会释放该锁
    static class Blocked implements Runnable {
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    try {
                        Thread.sleep(100*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}