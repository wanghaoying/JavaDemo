package com.forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoin 框架测试， ForkJoin框架使用了工作窃取算法，可以提高工作效率
 */
public class CountTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 2; // 阈值

    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;

        if((end-start) <= THRESHOLD){
            for(int i = start; i<=end; i++){
                sum += i;
            }
        }else {
            int mid = (start+end) / 2;
            // 生成子任务
            CountTask leftTask = new CountTask(start, mid);
            CountTask rightTask = new CountTask(mid+1, end);

            // 将fork出的子任务添加到ForkJoinPool中的ForkJoinTask数组中
            leftTask.fork();
            rightTask.fork();

            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            sum = leftResult + rightResult;
        }

        return sum;
    }

    /**
     * fork/join框架目的是对计算密集型任务进行一种加强
     * 主要是由于需要初始化数组和线程等系统资源，创建这些资源需要花费一定的时间
     * @param args
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(1,10000);

        Future<Integer> result = forkJoinPool.submit(countTask);

        try {
            System.out.println("计算结果："+result.get());
            System.out.println("活跃线程数："+forkJoinPool.getActiveThreadCount());
            System.out.println("发生工作窃取的次数："+forkJoinPool.getStealCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("fork/join框架消费的时间："+(end-startTime));

        startTime = System.currentTimeMillis();
        int sum = 0;
        for (int i = 0; i <= 10000; i++) {
            sum += i;
        }
        System.out.println(sum);
        end = System.currentTimeMillis();
        System.out.println(end-startTime);
    }
}
