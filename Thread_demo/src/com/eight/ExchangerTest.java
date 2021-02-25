package com.eight;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {

    private static final Exchanger<String> ex = new Exchanger<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(() -> {
            String A = "这个数据是A字符串";
            try {
                Thread.sleep(5000);
                ex.exchange(A);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            String B = "这个数据是B字符串";
            try {
                String A = ex.exchange(B);
                System.out.println("A 和 B 数据是否一致：" + A.equals(B) + ", A录入的是："
                + A +"，B录入的是：" + B);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.shutdown();
    }
}
