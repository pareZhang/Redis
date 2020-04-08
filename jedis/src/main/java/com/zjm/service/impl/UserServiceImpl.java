package com.zjm.service.impl;

import com.zjm.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author pareZhang
 * @Date 2020/4/8 13:10
 **/
@Service
@Log  // private Logger logger= LoggerFactory.getLogger(JedisConfig.class);
public class UserServiceImpl implements UserService {

    @Autowired
    private JedisPool jedisPool;  //JedisPool jedis连接池

    /**
     * Redis有什么命令，Jedis就有什么方法
     * Redis 的 String类型
     * 需求：用户输入一个key，先判断redis中是否存在该数据，如果存在，再redis中进行查询,并返回，如果不存在，在MYSQL中查询，
     * 将结果赋值给Redis，并返回
     * @param key
     * @return
     */
    @Override
    public String getString(String key) {
        String val=null;
        //1.得到jedis对象
        Jedis jedis = jedisPool.getResource();
        //2.判断key是否存在Redis
        if (jedis.exists(key)){
           log.info("查询redis中的数据");
           val=jedis.get(key);
        }else {
             val="我是mysql数据库中的数据";
            log.info("查询的是mysql数据库");
            jedis.set(key,val);
        }
        //3.关闭连接
        jedis.close();
        return val;
    }
}
