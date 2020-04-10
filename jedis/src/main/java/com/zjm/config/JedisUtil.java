package com.zjm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author pareZhang
 * @Date 2020/4/10 11:06
 **/
@Component
public class JedisUtil {

    /**
     * 获取jedis资源
     */
    @Autowired
    private JedisPool jedisPool;
    public Jedis getJedis(){
        return jedisPool.getResource();
    }

    /**
     * 释放jedis连接
     * @param jedis
     */
    public void close(Jedis jedis){
        if (jedis!=null){
            jedis.close();
        }
    }
    //......封装工具类？Redis有什么命令，Jedis就有什么方法
    public long caclTimeHour(int hours){
        long num=hours*60*60;
        return num;
    }
}
