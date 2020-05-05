package com.zjm.service.impl;

import com.zjm.service.SetCacheService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author pareZhang
 * @Date 2020/5/5 13:12
 **/
@Service
@Log
public class SetCacheServiceImpl implements SetCacheService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 向set中批量添加值
     * @param key 键
     * @param objects 值
     * @return true 成功 false 失败
     */
    @Override
    public boolean add(String key, Object... objects) {
        try {
            redisTemplate.opsForSet().add(key,objects);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向set中i两添加值
     * @param key  键
     * @param expireTime 过期时间
     * @param values 值
     * @return true成功 false 失败
     */
    @Override
    public boolean add(String key, int expireTime, Object... values) {
        try {
            redisTemplate.opsForSet().add(key,values);
            if (expireTime>0){
                redisTemplate.expire(key,expireTime,TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取set中的值
     * @param key 键
     * @return 值
     */
    @Override
    public Set<Object> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 返回set的长度
     * @param key 键
     * @return 长度
     */
    @Override
    public long size(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断给定的元素是否在set中
     * @param key 键
     * @param o 给定的元素
     * @return true 存在 false 不存在
     */
    @Override
    public boolean isMember(String key, Object o) {
        return redisTemplate.opsForSet().isMember(key,o);
    }

    /**
     * 转移set中的元素到目标set
     * @param key 键
     * @param value 要转移的元素
     * @param k1 目标set
     * @return true 成功 false 失败
     */
    @Override
    public boolean move(String key, Object value, String k1) {
        return redisTemplate.opsForSet().move(key, value, k1);
    }

    /**
     * 删除set中的元素
     * @param key 键
     * @return 返回删除的元素
     */
    @Override
    public Object pop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 批量删除set中的元素
     * @param key 键
     * @param values 要移除的元素
     * @return 返回移除元素个数
     */
    @Override
    public long remove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 匹配获取键值对
     * @param key 键
     * @param options 选项
     * @return 返回键值对
     */
    @Override
    public Cursor<Object> scan(String key, ScanOptions options) {
        return redisTemplate.opsForSet().scan(key, options);
    }

    /**
     * 通过集合求差值
     * @param key 键
     * @param list 要比较的keys
     * @return 返回差值
     */
    @Override
    public Set<Object> difference(String key, List list) {
        return redisTemplate.opsForSet().difference(key,list);
    }
    /**
     * 通过集合求差值
     * @param key 键
     * @param key1 要比较的key
     * @return 返回差值
     */
    @Override
    public Set<Object> difference(String key, String key1) {
        return redisTemplate.opsForSet().difference(key, key1);
    }

    /**
     * 将求出来的元素差值保存
     * @param key 键
     * @param key1 比较的key
     * @param destKey 目标key
     */
    @Override
    public void differenceAndStore(String key, String key1, String destKey) {
        redisTemplate.opsForSet().differenceAndStore(key, key1, destKey);
    }
    /**
     * 将求出来的元素差值保存
     * @param key 键
     * @param otherKeys 比较的keys
     * @param destKey 目标key
     */
    @Override
    public void differenceAndStore(String key, List otherKeys, String destKey) {
        redisTemplate.opsForSet().differenceAndStore(key,otherKeys,destKey);
    }

    /**
     * 获取两个set中的交集
     * @param key 键
     * @param key1 比较的key
     * @return
     */
    @Override
    public Set<Object> interSect(String key, String key1) {
        return redisTemplate.opsForSet().intersect(key, key1);
    }
    /**
     * 获取两个set中的交集
     * @param key 键
     * @param list 比较的keys
     * @return
     */
    @Override
    public Set<Object> interSect(String key, List list) {
        return redisTemplate.opsForSet().intersect(key,list);
    }

    /**
     * 获取两个set交集后保存到目标set中
     * @param key 键
     * @param key1 比较的键
     * @param destKey
     */
    @Override
    public void intersectAndStore(String key, String key1, String destKey) {
        redisTemplate.opsForSet().intersectAndStore(key, key1, destKey);
    }

    /**
     * 获取set交集后保存到目标set中
     * @param key 键
     * @param otherKeys 比较的keys
     * @param destKey 目标key
     */
    @Override
    public void intersectAndStore(String key, List otherKeys, String destKey) {
        redisTemplate.opsForSet().intersectAndStore(key,otherKeys,destKey);
    }

    /**
     *获取两个set的合集
     * @param key 键
     * @param key1 比较的key
     * @return 合集
     */
    @Override
    public Set<Object> union(String key, String key1) {
        return redisTemplate.opsForSet().union(key, key1);
    }

    /**
     * 获取set的合集
     * @param key key
     * @param set 比较的keys
     * @return 合集
     */
    @Override
    public Set<Object> union(String key, Set set) {
        return redisTemplate.opsForSet().union(key,set);
    }

    /**
     * 获取两个set的合集并保存到目标set
     * @param key 键
     * @param key1 比较的key
     * @param destKey 目标key
     */
    @Override
    public void unionAndStore(String key, String key1, String destKey) {
        redisTemplate.opsForSet().unionAndStore(key, key1, destKey);
    }

    /**
     * 获取set的合集并保存到目标set
     * @param key 键
     * @param list 比较的keys
     * @param destKey 目标key
     */
    @Override
    public void unionAndStore(String key, List list, String destKey) {
        redisTemplate.opsForSet().unionAndStore(key,list,destKey);
    }

    /**
     * 返回set中随机的一个元素
     * @param key
     * @return 随机的一个元素
     */
    @Override
    public Object randomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 返回set中随机的几个元素
     * @param key
     * @param count 要返回几个
     * @return 返回随机元素List
     */
    @Override
    public List<Object> randomMembers(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }
    /**
     * 获取去重的随机元素
     * @param key 键
     * @param count 数量
     * @return  返回随机元素set
     */
    @Override
    public Set<Object> distinctRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * 删除键
     * @param keys
     */
    @Override
    public void del(String... keys) {
        if (keys!=null&&keys.length>0){
            if (keys.length==1){
                redisTemplate.delete(keys[0]);
            }else {
                redisTemplate.delete(CollectionUtils.arrayToList(keys));
            }
        }
    }

    @Override
    public boolean expire(String key, long seconds) {
        return false;
    }
}
