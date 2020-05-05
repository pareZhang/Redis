package com.zjm.service;

import java.util.List;

/**
 * @Author pareZhang
 * @Date 2020/5/1 20:42
 **/
public interface ListCacheService {
    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return  true 成功 false 失败
     */
    boolean lpushAll(String key, List<Object> value);

    /**
     * 将list放入缓存
     * @param key   键
     * @param value 值
     * @param time  时间（秒）
     * @return   true 成功 false 失败
     */
   boolean lpushAll(String key, List <Object> value, long time);

    /**
     *将list放入缓存
     * @param key 键
     * @param value 值
     * @return true 成功 false 失败
     */
   boolean rpushAll(String key, List <Object> value);

    /**
     * 将list放入缓存
     * @param key  键
     * @param value 值
     * @param time  时间（秒）
     * @return  true 成功  false 失败
     */
   boolean rpushAll(String key, List <Object> value, long time);

    /**
     * 向集合最左边添加元素值
     * @param key  键
     * @param object 值
     * @return true成功 false 失败
     */
    Boolean lpush(String key, Object object);

    /**
     * 把最后一个参数值放到指定集合的第一个出现中间参数的前面，如果中间参数值存在的话。
     * @param key  键
     * @param pivot 中间参数值
     * @param object 要放的值
     * @return true 成功  false 失败
     */
    Boolean lpush(String key, Object pivot, Object object);

    /**
     * 集合中第一次出现第二个参数变量元素的右边添加第三个参数变量的元素值。
     * @param key 键
     * @param pivot 中间参数
     * @param object 要放的值
     * @return true 成功 false  失败
     */
    Boolean rpush(String key, Object pivot,Object object);

    /**
     * 向集合最右边添加元素
     * @param key 键
     * @param object 值
     * @return true 成功 false 失败
     */
    Boolean rpush(String key, Object object);

    /**
     * 在变量左边添加元素值
     * @param key 键
     * @param expireTime 超时时间
     * @param objects 值
     * @return true 成功 false 失败
     */
    Boolean lpush(String key, int expireTime, Object... objects);

    /**
     * 在变量右边添加元素值
     * @param key 键
     * @param expireTime 超时时间
     * @param objects 值
     * @return true 成功 false 失败
     */
    Boolean rpush(String key, int expireTime, Object... objects);

    /**
     * 如果集合存在则向左边添加元素，不存在不加
     * @param key 键
     * @param object 值
     * @return true 成功 false 失败
     */
    boolean lPushIfPresent(String key, Object object);

    /**
     * 如果集合存在则向右边添加元素，不存在不加
     * @param key  键
     * @param object 值
     * @return true 成功 false 失败
     */
    boolean rPushIfPresent(String key, Object object);

    /**
     * 移除集合左边第一个元素
     * @param key 键
     * @return 返回左边第一个元素
     */
    Object lpop(String key);

    /**
     * 移除集合最右边的元素,一般用在队列取值
     * @param key 键
     * @return 返回最右边的元素
     */
    Object rpop(String key);

    /**
     * 移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。一般用在队列取值
     * @param key 键
     * @param time 时间
     * @return 左边的元素
     */
    Object lpop(String key,long time);

    /**
     * 移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。一般用在队列取值
     * @param key 键
     * @param time 值
     * @return 右边的元素
     */
    Object rpop(String key,long time);

    /**
     * 获取指定区间的值
     * @param key 键
     * @param start 开始位置
     * @param end   结束位置 为-1指结尾的位置 start 0 end -1 取所有值
     * @return
     */
    List<Object> lrange(String key, long start, long end);

    /**
     * 取集合长度
     * @param key 键
     * @return 返回长度
     */
    Long llen(String key);

    /**
     * 在集合的指定位置插入元素,如果指定位置已有元素，则覆盖，没有则新增，超过集合下标+n则会报错。
     * @param key 键
     * @param index 位置
     * @param value 值
     */
    void set(String key, Long index, Object value);

    /**
     * 获取集合指定位置的值
     * @param key 键
     * @param index 位置
     * @return 值
     */
    Object lindex(String key, Long index);

    /**
     * 从存储在键中的列表中删除等于值的元素的第一个计数事件
     * count> 0：删除等于从左到右移动的值的第一个元素；
     * count< 0：删除等于从右到左移动的值的第一个元素；
     * count = 0：删除等于value的所有元素。
     * @param key 键
     * @param count
     * @param object
     * @return
     */
    long remove(String key,long count,Object object);

    /**
     * 截取集合元素长度，保留长度内的数据。
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     */
    void trim(String key,long start,long end);

    /**
     * 除集合中右边的元素，同时在左边加入一个元素。
     * @param key 键
     * @param key1 加入的元素
     * @return  返回右边的元素
     */
    Object rightPopAndLeftPush(String key,String key1);

    /**
     * 移除集合中右边的元素在等待的时间里，同时在左边添加元素，如果超过等待的时间仍没有元素则退出。
     * @param key 键
     * @param key1 值
     * @param timeout 超时时间
     * @return 返回移除右边的元素
     */
    Object rightPopAndLeftPush(String key,String key1, long timeout);

    /**
     * 删除
     * @param keys 键
     */
    void del(String... keys);

    /**
     * 设置过期时间
     * @param key 键
     * @param seconds 超时时间
     * @return true 成功 false 失败
     */
    boolean expire(String key, long seconds);
}
