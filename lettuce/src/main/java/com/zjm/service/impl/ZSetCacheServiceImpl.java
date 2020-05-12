package com.zjm.service.impl;

import com.zjm.service.ZSetCacheService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @Author pareZhang
 * @Date 2020/5/6 16:11
 **/
@Service
@Log
public class ZSetCacheServiceImpl implements ZSetCacheService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 添加元素到变量中同时指定元素分值
     * @param key 键
     * @param value 值
     * @param score 分数
     * @return
     */
    @Override
    public boolean add(String key, Object value, double score) {
        try {
            redisTemplate.opsForZSet().add(key,value,score);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 通过TypedTuple方式新增数据
     * @param key 键
     * @param tuples 元组
     */
    @Override
    public void add(String key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
        redisTemplate.opsForZSet().add(key, tuples);
    }

    /**
     * 获取有序集合指定区间内的元素 start为0 end为-1 代表取全部元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public Set<Object> range(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }
    /**
     * 索引倒叙排列指定区间元素
     * @param key  键
     * @param start 开始位置
     * @param end   结束位置
     * @return  返回倒排后的结果
     */
    @Override
    public Set<Object> reverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 倒叙排列指定分值区间元素
     * @param key
     * @param min 最小score
     * @param max 最大score
     * @return
     */
    @Override
    public Set<Object> reverseRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 倒叙排列从给定下标和给定长度分值区间元素
     * @param key 键
     * @param min 最小score
     * @param max 最大score
     * @param offset 偏移量
     * @param count 数量
     * @return 返回set
     */
    @Override
    public Set<Object> reverseRangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    /**
     * 用于获取满足非score的排序取值，这个排序只有在有相同分数情况下才能使用，如果有不同的分数则返回值不确定
     * @param key 键
     * @param range
     * @return 返回set
     */
    @Override
    public Set<Object> rangeByLex(String key, RedisZSetCommands.Range range) {
        return redisTemplate.opsForZSet().rangeByLex(key, range);
    }

    /**
     * 用于获取满足非score的设置下标开始的长度排序取值
     * @param key 键
     * @param range 范围
     * @param limit 限制区域
     * @return 返回set
     */
    @Override
    public Set<Object> rangeByLex(String key, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit) {
        return redisTemplate.opsForZSet().rangeByLex(key, range, limit);
    }

    /**
     * 获取变量中元素的个数
     * @param key
     * @return
     */
    @Override
    public long zCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取分数区间值的个数
     * @param key
     * @param min
     * @param max
     * @return
     */
    @Override
    public long count(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 修改有序集合中元素的分值
     * @param key  键
     * @param value 值
     * @param delta 修改的分数
     * @return
     */
    @Override
    public double incrementScore(String key, Object value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 获取有序集合的元素的分值
     * @param key
     * @param o
     * @return
     */
    @Override
    public double score(String key, Object o) {
        return redisTemplate.opsForZSet().score(key,o);
    }

    /**
     * 根据设置的score获取区间值，从给定下标到和给定长度获取最终值
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @param offset 偏移值
     * @param count 取的长度
     * @return 返回set
     */
    @Override
    public Set<Object> rangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    /**
     * 获取ZSetOperations.TypedTuple的区间值
     * @param key   键
     * @param start 开始score值
     * @param end   结束score值
     * @return  返回区间值
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> rangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 索引倒叙排列区间值
     * @param key   键
     * @param start 开始
     * @param end   结束
     * @return 返回 set
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 通过分值获取Set<ZSetOperations.TypedTuple的区间值
     * @param key 键
     * @param min 最小分值
     * @param max 最大分值
     * @return 返回set
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> rangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * 通过分值获取Set<ZSetOperations.TypedTuple的区间值，从给定下标和给定长度获取最终值
     * @param key 键
     * @param min 最小分值
     * @param max 最大分值
     * @param offset 偏移量
     * @param count 数量
     * @return 返回set
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> rangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * 倒序排序获取RedisZSetCommands.Tuples的分值区间值
     * @param key 键
     * @param min 最小分值
     * @param max 最大分值
     * @return
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }
    /**
     * 倒序排序获取RedisZSetCommands.Tuples的从给定下标和给定长度分值区间值
     * @param key 键
     * @param min 最小分值
     * @param max 最大分值
     * @param offset 偏移量
     * @param count 数量
     * @return 返回set
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * 获取元素的索引 下标开始为0
     * @param key 键
     * @param o 元素
     * @return 返回下标
     */
    @Override
    public long rank(String key, Object o) {
        return redisTemplate.opsForZSet().rank(key, o);
    }

    /**
     * 匹配获取键值对 ScanOptions.NONE为获取全部键值对
     * ScanOptions.scanOptions().match("c").build() 匹配获取键位map1的键值对，不能模糊匹配。
     * @param key     键
     * @param options 选项
     * @return  返回键值对
     */
    @Override
    public Cursor<ZSetOperations.TypedTuple<Object>> scan(String key, ScanOptions options) {
        return redisTemplate.opsForZSet().scan(key,options);
    }

    /**
     * 获取倒序排列的索引值
     * @param key 键
     * @param o   元素
     * @return    返回倒叙排列的索引值
     */
    @Override
    public long reverseRank(String key, Object o) {
        return redisTemplate.opsForZSet().reverseRank(key, o);
    }

    /**
     * 获取两个集合的交集存到第三个集合中
     * @param key  键
     * @param key1 键1
     * @param destKey 目标集合键
     * @return 返回交集长度
     */
    @Override
    public long intersectAndStore(String key, String key1, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, key1, destKey);
    }

    /**
     * 获取多个集合的交集存到第三个集合中
     * @param key  键
     * @param list 要比较的集合们的键
     * @param destKey 目标集合键
     * @return 返回交集长度
     */
    @Override
    public long intersectAndStore(String key, List list, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key,list,destKey);
    }

    /**
     * 获取两个几个的合集存到第三个集合中
     * @param key 键
     * @param key1 要比较的键
     * @param destKey 目标集合键
     * @return 返回交集长度
     */
    @Override
    public long unionAndStore(String key, String key1, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, key1, destKey);
    }

    /**
     * 获取多个集合的合集存到目标集合中
     * @param key 键
     * @param list 比较的keys
     * @param destKey 目标集合键
     * @return 集合长度
     */
    @Override
    public long unionAndStore(String key, List list, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key,list,destKey);
    }

    /**
     * 根据元素值批量移除元素
     * @param key 键
     * @param values 要删除的元素
     * @return 删除的数量
     */
    @Override
    public long remove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 根据分值移除区间元素
     * @param key
     * @param min
     * @param max
     * @return
     */
    @Override
    public long removeRangeByStore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 根据索引值移除区间元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public long removeRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 删除指定的key的缓存
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
}
