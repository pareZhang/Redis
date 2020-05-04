package com.zjm.service.impl;

import com.zjm.po.User;
import com.zjm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author pareZhang
 * @Date 2020/4/18 18:00
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Resource(name = "redisTemplate")
    private ValueOperations<String,String> string; // redisTemplate.opsForValue()
    @Resource(name="redisTemplate")  //K user ,HK ID,HV Object
    private HashOperations<String, String, User>  hash;

    /**
     * Redis有什么命令，Jedis就有什么方法
     * Lettuce---->RedisTemplate (Jedis/......)进一步的封装
     * Redis 的 String类型
     * 需求：用户输入一个key，先判断redis中是否存在该数据，如果存在，再redis中进行查询,并返回，如果不存在，在MYSQL中查询，
     * 将结果赋值给Redis，并返回
     * @param key
     * @return
     */
    @Override
    public String getString(String key) {
        //exists
        if (redisTemplate.hasKey(key)){
            //如果redis存在key，直接查询返回
            log.info("Redis中查询出来的");
          // return (String) redisTemplate.opsForValue().get(key);
            return string.get(key);
        } else{
            //如果redis中没有key，模拟从mysql中查询出来的数据
            String val="RedisTemplate模板学习lettuce客户端";
            log.info("mysql中查询出来的："+val);
           // redisTemplate.opsForValue().set(key,val);
            string.set(key,val);
            log.info("在mysql中查询出来的结果存入Redis中");
            return val;
        }
    }
    /**
     * 测试String类型
     * 需求：用户输入一个redis数据，该key的有效期为28小时
     */
    @Override
    public void expireStr(String key, String value){
        //存值
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key,2, TimeUnit.HOURS);
    }

    /**
     * 测试hash类型 演示
     * @param id
     * @return
     *
     * 根据ID查询用户对象信息
     * 先判断Redis中是否存在该key
     * 如果不存在，查询mysql，并将结果添加到redis，并返回
     * 如果存在，直接在redis中查询，并返回
     */
    @Override
    public User selectById(String id) {
        //redisTemplate.hasKey() 判断整个key是否存在
        //user:id
       if (redisTemplate.opsForHash().hasKey(User.getKeyName(),id)){
           log.info("从Redis中查询的数据");
         // return (User) redisTemplate.opsForHash().get(User.getKeyName(),id);
         return   hash.get(User.getKeyName(),id);
       }else {
           //模拟从redis中查询出来的数据
           log.info("从MySQL中查询的数据：");
           User user=new User();
           user.setId(id);
           user.setName("徐志摩");
           user.setAge(32);
           /*
            @param h 用户实体  user
            @param hk 用户主键 id
            @param hv 整个对象
            */
          // redisTemplate.opsForHash().put(User.getKeyName(),id,user);
           hash.put(User.getKeyName(),id,user);
           return user;
       }
    }
    /**
     * 问题1：出现了很多相同的字符串。---->提取出来
     *     答1：声明一个工具类
     *     答2：在实体bean内声明一个方法
     *
     * 问题2：强制类型转换问题？
     * 问题3：redisTemplate.opsForHash() 写很长一串？
     *     答：
     */
}
