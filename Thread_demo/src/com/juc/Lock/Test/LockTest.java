package com.juc.Lock.Test;

import org.junit.Test;
import sun.awt.windows.ThemeReader;

import javax.swing.plaf.TableHeaderUI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockTest {

    static boolean flag = true;

    static ReentrantLock lock = new ReentrantLock();

    @Test
    public void hashMapTest(){
        HashMap hashMap = new HashMap();
        hashMap.put(1,1);
        hashMap.put(2,17);
        hashMap.put(3,17);
        hashMap.put(4,17);
        hashMap.put(5,17);
        hashMap.put(6,17);
        hashMap.put(7,17);
        hashMap.put(8,17);
        hashMap.put(9,17);
        hashMap.put(10,17);
        hashMap.put(11,17);
        hashMap.put(12,17);
        hashMap.put(13,17);
    }

    /**
     * 这个方法是为了验证jsr133中提到的Thread.sleep 和 Thread.yield 都没有任何同步语义。
     * 特别是，编译器不需要在 sleep 或 yield 调用之前将寄存器中缓存的写操作回写到共享主存，
     * 也不需要在 sleep 或 yield 调用之后重新加载缓存在寄存器里的值。
     * @throws InterruptedException
     */
    @Test
    public void sleepTest() throws InterruptedException {
        new Thread(()->{
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = false;
            System.out.println("已经修改");
            try {
                Thread.sleep(10000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();

        while(flag){
            // 加了sout之后会发生可以感知到flag的变化，是因为sout中用到了synchronized关键字
            System.out.println("waiting");
        }
        System.out.println("all is over");
    }

    @Test
    public void lockTest(){
        new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1000*60*60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        lock.lock();
        try {

        } finally {
            lock.unlock();
        }
    }

    @Test
    public void rwLockGetReadLockCountTest(){
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        Lock readLock = lock.readLock();
        Lock writeLock = lock.writeLock();

        readLock.lock();
        try {
            readLock.lock();
            try {
                System.out.println(lock.getReadLockCount());
            }finally {
                readLock.unlock();
            }
        }finally {
            readLock.unlock();
        }
    }
}
