package com.redis.utils;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JRutilsTest {

    @Test
    public void getJedisTest(){
        Jedis jedis = JRutils.getJedis();
        System.out.println(jedis);
        jedis.close();
    }


}
