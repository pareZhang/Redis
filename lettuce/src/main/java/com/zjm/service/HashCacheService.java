package com.zjm.service;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author pareZhang
 * @Date 2020/5/4 18:59
 **/
public interface HashCacheService {
    /**
     * 获取map中的某个值
     * @param key 键
     * @param item 项
     * @return 值
     */
    Object hget(String key,String item);

    /**
     * 获取hashkey对应的所有键值
     * @param key 键
     * @return 对应的多个值
     */
    Map<Object,Object> hmget(String key);

    /**
     * 以map集合的形式添加键值对
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    boolean hmset(String key,Map<String,Object> map);

    /**
     * hset并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间（秒）
     * @return true 成功 false 失败
     */
    boolean hmset(String key,Map<String,Object> map,long time);

    /**
     * 向一张哈希表中放入数据，如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 fasle 失败
     */
    boolean hset(String key,String item,Object value);

    /**
     * 向一张哈希表中放入数据，如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true成功 false 失败
     */
    boolean hset(String key,String item,Object value,long time);

    /**
     * 删除哈希表中的值
     * @param key 键
     * @param item 项 可以是多个，不能为null
     */
    void hdel(String key,Object...item);

    /**
     * 判断hash表中是否有该项的值
     * @param key 键
     * @param item 项
     * @return true 存在 false 不存在
     */
    boolean hHasKey(String key,String item);

    /**
     * hash递增，如果不存在，就会创建一个，并把新增后的值返回
     * @param key 键
     * @param item 项
     * @param by 要增大几 大于零
     * @return
     */
    long hIncr(String key,String item,long by);

    /**
     * hash递减
     * @param key 键
     * @param item 项
     * @param by 要减少几 小于零
     * @return
     */
    long hDecr(String key,String item,long by);

    /**
     * 获取指定变量中的hashMap值
     * @param key
     * @return
     */
    List<Object> values(String key);

    /**
     * 获取变量中的键
     * @param key
     * @return
     */
    Set<Object> keys(String key);

    /**
     * 获取变量的长度
     * @param key
     * @return 长度
     */
    long size(String key);

    /**
     *以集合的方式获取变量中的值
     * @param key
     * @param list
     * @return
     */
    List mutiGet(String key, List list);

    /**
     * 如果变量值存在，在变量中可以添加不存在的的键值对
     * 如果变量不存在，则新增一个变量，同时将键值对添加到该变量。
     * @param key
     * @param hashKey
     * @param value
     */
    void putIfAbsent(String key, String hashKey, Object value);

    /**
     * 匹配获取键值对，ScanOptions.NONE为获取全部键对
     * ScanOptions.scanOptions().match("map1").build()
     * 匹配获取键位map1的键值对，不能模糊匹配。
     * @param key
     * @param options
     * @return
     */
    Cursor<Map.Entry<Object,Object>> scan(String key, ScanOptions options);

    /**
     * 删除变量中的键值对，可以传入多个参数，删除多个键值对
     * @param key
     * @param hashKeys map中的key
     */
    void delete(String key,String...hashKeys);

    /**
     * 设置过期时间
     * @param key
     * @param seconds
     * @return
     */
    boolean expire(String key,long seconds);

    /**
     * 删除
     * @param keys
     */
    void del(String...keys);

    long getExpire(String key);
}
