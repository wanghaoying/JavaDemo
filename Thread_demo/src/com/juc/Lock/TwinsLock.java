package com.juc.Lock;

import sun.misc.Unsafe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TwinsLock implements Lock {
    private final Sync sync = new Sync(2);
    private static final class Sync extends AbstractQueuedSynchronizer{
        public Sync(int state){
            if(state<0){
                throw new IllegalArgumentException("state must large than 0");
            }
            setState(state);
        }

        @Override
        protected int tryAcquireShared(int acquire) {
            for(;;){
                int current = getState();
                int newState = current - acquire;
                if(newState < 0 || compareAndSetState(current,newState)){
                    return newState;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int release) {
            for(;;){
                int current = getState();
                int newState = current + release;
                if(compareAndSetState(current,newState)){
                    System.out.println(getSharedQueuedThreads());
                    return true;
                }
            }
        }
    }

    @Override
    public void lock() {
        sync.acquireShared(1);
    }


    @Override
    public void lockInterruptibly() throws InterruptedException {

    }


    @Override
    public boolean tryLock() {
        return false;
    }


    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public void unlock() {
        sync.releaseShared(1);
    }


    @Override
    public Condition newCondition() {
        return null;
    }
}
