package com.zjm.service.impl;

import com.zjm.config.JedisUtil;
import com.zjm.po.User;
import com.zjm.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author pareZhang
 * @Date 2020/4/8 13:10
 **/
@Service
@Log  // private Logger logger= LoggerFactory.getLogger(JedisConfig.class);
public class UserServiceImpl implements UserService {

    @Autowired
    //JedisPool jedis连接池
    private JedisPool jedisPool;
    @Autowired
    private JedisUtil jedisUtil;

    //TODO  String类型的演示
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
    /**
     * 测试String类型
     * 需求：用户输入一个redis数据，该key的有效期为28小时
     */
    @Override
    public void expireStr(String key, String value){
        Jedis jedis = jedisUtil.getJedis();
        //key默认永久有效
        jedis.set(key, value);
        //28小时？
        long diff=jedisUtil.caclTimeHour(28);
        jedis.expire(key, (int) diff);
        log.info(key+"\t设置值:"+value+"\t"+"ttl:"+diff);

        jedisUtil.close(jedis);
    }

    /**
     * TODO Hash类型演示
     * 存一个对象
     * 需求分析：根据用户的ID查询用户信息
     * 用户在前端传入一个ID编号，根据用户的ID查询用户的对象信息
     * 先判断如果redis中存在，直接返回，如果redis中不存在，查询MySQL，并将查询到的结果赋值给redis，并返回
     */
    @Override
    public User selectById(String id){
        //实体类名：id
        String key="user:"+id;
        User user=new User();
        //1.得到jedis对象
        Jedis jedis = jedisUtil.getJedis();
        //2.实现业务逻辑判断
        if (jedis.exists(key)){
            //存在
            log.info("--------->查询的是Redis的数据");
           Map<String,String> map= jedis.hgetAll(key);
           user.setId(map.get("id"));
           user.setAge(Integer.parseInt(map.get("age")));
           user.setName(map.get("name"));

        }else {
            //不存在
            //new一个对象，模拟从MySQL中查出的数据
            user.setId(id);
            user.setAge(25);
            user.setName("周杰伦");
            log.info("--------->查询的是MYSQL中的数据"+user);
            //存入redis
            Map<String,String> map=new HashMap<>();
            map.put("id",user.getId());
            map.put("age",user.getAge()+"");
            map.put("name",user.getName());
            jedis.hmset(key,map);
            log.info("------->往redis中进行存");
        }

        //3.关闭jedis连接
        jedisUtil.close(jedis);
        return user;
    }
}
