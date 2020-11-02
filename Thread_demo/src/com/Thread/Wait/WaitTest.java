package com.Thread.Wait;

//https://blog.csdn.net/liuguangqiang/article/details/49180319

public class WaitTest {

    public static void main(String[] args)
    {
        ThreadB b=new ThreadB();
        b.start();
        System.out.println("b is start....");
        synchronized(b)//对b这个对象加锁
        {
            try
            {
                System.out.println("Waiting for b to complete...");
                b.wait();//在当前代码块，暂时释放掉b的锁，等待notify
                System.out.println("Completed.Now back to main thread");
            }catch (InterruptedException e){}
        }
        System.out.println("Total is :"+b.total);
    }
}


class ThreadB extends Thread
{
    int total;

    @Override
    public void run()
    {
        synchronized(this)
        {
            System.out.println("ThreadB is running..");
            for (int i=0;i<100;i++ )
            {
                total +=i;
                System.out.println("total is "+total);
            }
//            notify();
        }
    }
}
