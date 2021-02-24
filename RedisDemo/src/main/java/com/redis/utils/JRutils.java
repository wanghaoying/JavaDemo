package com.redis.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.ResourceBundle;

public class JRutils {
    static JedisPool jp = null;
    static String host = null;
    static int maxTotal;
    static int maxIdle;
    static int port;

    static {
        ResourceBundle resource = ResourceBundle.getBundle("redis");
        host = resource.getString("redis.host");
        port = Integer.parseInt(resource.getString("redis.port"));
        maxIdle = Integer.parseInt(resource.getString("redis.maxIdle"));
        maxTotal = Integer.parseInt(resource.getString("redis.maxTotal"));

        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);

        jp = new JedisPool(config, host, port);
    }


    public static Jedis getJedis(){
        return jp.getResource();
    }
}
