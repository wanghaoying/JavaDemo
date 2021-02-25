package com.eight;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class BankWaterService implements Runnable {

    private CyclicBarrier c = new CyclicBarrier(4, this);

    private ExecutorService executor = Executors.newFixedThreadPool(4);

    private HashMap<String,Integer> hashMap = new HashMap<>();

    private void count(){
        for (int i = 0; i < 4; i++) {
            executor.execute(() ->{
                hashMap.put(Thread.currentThread().getName(),1);
                try {
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }

    @Override
    public void run() {
        int result = 0;
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            result += entry.getValue();
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        BankWaterService bankWaterService = new BankWaterService();
        bankWaterService.count();
    }
}
