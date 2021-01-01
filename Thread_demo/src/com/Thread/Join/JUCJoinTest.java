package com.Thread.Join;

public class JUCJoinTest {

    static Thread thread = Thread.currentThread();

    public static void main(String[] args) throws InterruptedException {
        Thread previous = thread;

        for (int i = 0; i < 10; i++) {
            Thread tempThread = new Thread(new JoinThread(previous),String.valueOf(i));
            tempThread.start();
            previous = tempThread;
        }

        Thread.sleep(1000*2);

        System.out.println(Thread.currentThread().getName()+" is terminate.");
    }
}

class JoinThread extends Thread{
    private Thread joinThread;

    public JoinThread(Thread previous) {
        this.joinThread = previous;
    }

    @Override
    public void run(){
        try {
            joinThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " is terminal.");
    }
}
