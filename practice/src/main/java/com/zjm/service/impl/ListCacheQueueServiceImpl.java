package com.zjm.service.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author pareZhang
 * @Date 2020/5/3 18:38
 **/
@Service
@Log
public class ListCacheQueueServiceImpl {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //TODO --------->
    /**
     * 队列
     * 当用户完成付款后：
     * 淘宝会生成一个物流队列，（购买的商品，所在地---->收货地址）
     * 生成物流队列  （北京海淀区、收货地址：昆明学府路莲花小区6 404）
     * 1.商家发货
     * 2.快递小哥取货
     * 3.北京首都机场--昆明长水机场
     * 4.昆明长水机场--五华区
     * 5.五华区--学府路莲花小区
     * 6.收货
     */
    /**
     * 1.生成订单，物流队列
     */
    public void orderQueue(String orderId){
        String key="queue:"+orderId;
        stringRedisTemplate.opsForList().leftPush(key,"1.商家发货");
        stringRedisTemplate.opsForList().leftPush(key,"2.快递小哥取货");
        stringRedisTemplate.opsForList().leftPush(key,"3.北京首都机场--昆明长水机场");
        stringRedisTemplate.opsForList().leftPush(key,"4.昆明长水机场--五华区");
        stringRedisTemplate.opsForList().leftPush(key,"5.五华区--学府路莲花小区");
        stringRedisTemplate.opsForList().leftPush(key,"6.收货");
    }
    /**
     * 2.快递小哥触发 队列事件
     * 消费一个任务
     */
    public String orderTouch(String orderId){
        //执行成功的队列的key
        String keySucc="queue:"+orderId+":succ";
        //待执行的队列key
        String key="queue:"+orderId;
        return stringRedisTemplate.opsForList().rightPopAndLeftPush(key,keySucc);
    }
    /**
     * 3.快递公司
     * 关注：你的这个快递还有几项任务完成
     */
    public List<String> orderSelect(String orderId){
        //待执行的队列key
        String key="queue:"+orderId;
        return stringRedisTemplate.opsForList().range(key,0,-1);
    }
    /**
     * 4.用户
     * 关注：我的快递到哪了？
     */
    public List<String> orderSelectSucc(String orderId){
        //执行成功的队列的key
        String keySucc="queue:"+orderId+":succ";
        return stringRedisTemplate.opsForList().range(keySucc,0,-1);
    }
}
