package com.juc.Lock.Test;

import com.juc.Lock.TwinsLock;

import java.util.concurrent.locks.Lock;

public class TwinsLockTest {
    static final Lock lock = new TwinsLock();

    static class Worker extends Thread{
        @Override
        public void run() {
            while(true){
                lock.lock();
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        for (int i = 0; i < 10; i++) {
//            Worker worker = new Worker();
//            worker.setDaemon(true);
//            worker.start();
//        }
//
//        for (int i = 0; i < 10; i++) {
//            Thread.sleep(1000);
//            System.out.println();
//        }
        System.out.println(4 >>> 3);
    }
}
