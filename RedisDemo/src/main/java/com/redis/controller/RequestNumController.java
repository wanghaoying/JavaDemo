package com.redis.controller;

import com.redis.service.Impl.RequestNumServiceImpl;
import com.redis.utils.JRutils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

public class RequestNumController {
    RequestNumServiceImpl requestNumService = new RequestNumServiceImpl();

    public void doService(String id){
        Jedis jedis = JRutils.getJedis();
        try{
            String res = jedis.get("User:"+id);
            if(res == null){
                jedis.setex("User:"+id, 5, Long.MAX_VALUE-10+"");
            }
            Long num = jedis.incr("User:"+id);
            requestNumService.service(Long.MAX_VALUE-num);
        }catch (JedisDataException e){
//            e.printStackTrace();
            System.out.println("次数已达上限，请开启会员...");
        }
        finally {
            jedis.close();
        }

    }

    static class MyThread extends Thread{
        RequestNumController requestNumController = new RequestNumController();
        @Override
        public void run() {
            while (true){
                requestNumController.doService("basic_user");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        MyThread m1 = new MyThread();
        MyThread m2 = new MyThread();

        m1.start();
        m2.start();
    }

}
