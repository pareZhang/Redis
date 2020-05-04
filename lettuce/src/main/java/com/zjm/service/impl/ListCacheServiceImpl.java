package com.zjm.service.impl;

import com.zjm.po.Article;
import com.zjm.service.ListCacheService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author pareZhang
 * @Date 2020/5/1 21:29
 **/
@Service
@Log
public class ListCacheServiceImpl implements ListCacheService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource(name = "redisTemplate")
    private ListOperations<String, Article> opsForList;

    /**
     * list案例
     * 需求分析：显示当前最新的5条文章信息
     *    1.初始化，存5个文章信息
     *    2.查询主页显示5个文章
     *    3.添加文章
     *    4.删除文章
     */
    public void initArticle(){
        //当前时间
        LocalDateTime localDateTime=LocalDateTime.now();
        String dateTime=localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String key="article:top5";
        //1 在MySQL中查询5个文章
        log.info("模拟从MySQL中查询文章信息");
        List<Article>list=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Article article=new Article();
            article.setId("100"+i);
            article.setTitle("文章标题"+i);
            article.setContent("文章内容数据"+i);
            article.setClickNum(new Random().nextInt(1000)+1);
            article.setAuthor("pareZhang");
            article.setCreateDate(dateTime);
            list.add(article);
        }
        log.info("---->存入redis指定key中---->");
        opsForList.rightPushAll(key,list);
    }
    /**
     * 2.查询首页文章信息（5条）
     */
    public List<Article> selectArticleTop5(){
        String key="article:top5";
        log.info("查询首页文章信息（5条）");
        return opsForList.range(key,0,4);
    }
    /**
     * 3.添加文章
     */
    public long insertArticle(Article article){
        log.info("----->模拟从MySQL中查询出了文章信息----->");
        log.info("----->redis中也要进行添加----->");
        String key="article:top5";
        return opsForList.leftPush(key,article);
    }
    /**
     * 4.删除文章
     */
    public void delArticle(){
        log.info("----->redis里只保存最新的5条文章<-----");
        String key="article:top5";
        opsForList.trim(key,0,4);
    }
    /**
     * 以下演示了List所有命令的调用示例
     */

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return  true 成功 false 失败
     */
    @Override
    public boolean lpushAll(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().leftPushAll(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将list放入缓存
     * @param key   键
     * @param value 值
     * @param time  时间（秒）
     * @return   true 成功 false 失败
     */
    @Override
    public boolean lpushAll(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().leftPushAll(key,value);
            if (time>0){
                 expire(key,time);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     *将list放入缓存
     * @param key 键
     * @param value 值
     * @return true 成功 false 失败
     */
    @Override
    public boolean rpushAll(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 将list放入缓存
     * @param key  键
     * @param value 值
     * @param time  时间（秒）
     * @return  true 成功  false 失败
     */
    @Override
    public boolean rpushAll(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key,value);
            if (time>0){
                expire(key,time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 向集合最左边添加元素值
     * @param key  键
     * @param object 值
     * @return true成功 false 失败
     */
    @Override
    public Boolean lpush(String key, Object object) {
        try {
            redisTemplate.opsForList().leftPush(key,object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 把最后一个参数值放到指定集合的第一个出现中间参数的前面，如果中间参数值存在的话。
     * @param key  键
     * @param pivot 中间参数值
     * @param object 要放的值
     * @return true 成功  false 失败
     */
    @Override
    public Boolean lpush(String key, Object pivot, Object object) {
        try {
            redisTemplate.opsForList().leftPush(key,pivot,object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 集合中第一次出现第二个参数变量元素的右边添加第三个参数变量的元素值。
     * @param key 键
     * @param pivot 中间参数
     * @param object 要放的值
     * @return true 成功 false  失败
     */
    @Override
    public Boolean rpush(String key, Object pivot, Object object) {
        try {
            redisTemplate.opsForList().rightPush(key, pivot, object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向集合最右边添加元素
     * @param key 键
     * @param object 值
     * @return true 成功 false 失败
     */
    @Override
    public Boolean rpush(String key, Object object) {
        try {
            redisTemplate.opsForList().rightPush(key, object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 在变量左边添加元素值
     * @param key 键
     * @param expireTime 超时时间
     * @param objects 值
     * @return true 成功 false 失败
     */
    @Override
    public Boolean lpush(String key, int expireTime, Object... objects) {
        try {
            redisTemplate.opsForList().leftPushAll(key,objects);
            if (expireTime>0){
                expire(key,expireTime);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 在变量右边添加元素值
     * @param key 键
     * @param expireTime 超时时间
     * @param objects 值
     * @return true 成功 false 失败
     */
    @Override
    public Boolean rpush(String key, int expireTime, Object... objects) {
        try {
            redisTemplate.opsForList().rightPushAll(key,objects);
            if (expireTime>0){
                expire(key,expireTime);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 如果集合存在则向左边添加元素，不存在不加
     * @param key 键
     * @param object 值
     * @return true 成功 false 失败
     */
    @Override
    public boolean lPushIfPresent(String key, Object object) {
        try {
            redisTemplate.opsForList().leftPushIfPresent(key,object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 如果集合存在则向右边添加元素，不存在不加
     * @param key  键
     * @param object 值
     * @return true 成功 false 失败
     */
    @Override
    public boolean rPushIfPresent(String key, Object object) {
        try {
            redisTemplate.opsForList().rightPushIfPresent(key, object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 移除集合左边第一个元素
     * @param key 键
     * @return 返回左边第一个元素
     */
    @Override
    public Object lpop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }
    /**
     * 移除集合最右边的元素,一般用在队列取值
     * @param key 键
     * @return 返回最右边的元素
     */
    @Override
    public Object rpop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }
    /**
     * 移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。一般用在队列取值
     * @param key 键
     * @param time 时间
     * @return 左边的元素
     */
    @Override
    public Object lpop(String key, long time) {
        return redisTemplate.opsForList().leftPop(key,time, TimeUnit.MILLISECONDS);
    }
    /**
     * 移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。一般用在队列取值
     * @param key 键
     * @param time 值
     * @return 右边的元素
     */
    @Override
    public Object rpop(String key, long time) {
        return redisTemplate.opsForList().rightPop(key,time,TimeUnit.MILLISECONDS);
    }
    /**
     * 获取指定区间的值
     * @param key 键
     * @param start 开始位置
     * @param end   结束位置 为-1指结尾的位置 start 0 end -1 取所有值
     * @return
     */
    @Override
    public List<Object> lrange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }
    /**
     * 取集合长度
     * @param key 键
     * @return 返回长度
     */
    @Override
    public Long llen(String key) {
        return redisTemplate.opsForList().size(key);
    }
    /**
     * 在集合的指定位置插入元素,如果指定位置已有元素，则覆盖，没有则新增，超过集合下标+n则会报错。
     * @param key 键
     * @param index 位置
     * @param value 值
     */
    @Override
    public void set(String key, Long index, Object value) {
        redisTemplate.opsForList().set(key,index,value);
    }
    /**
     * 获取集合指定位置的值
     * @param key 键
     * @param index 位置
     * @return 值
     */
    @Override
    public Object lindex(String key, Long index) {
        return redisTemplate.opsForList().index(key,index);
    }
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
    @Override
    public long remove(String key, long count, Object object) {
        return redisTemplate.opsForList().remove(key, count, object);
    }
    /**
     * 截取集合元素长度，保留长度内的数据。
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     */
    @Override
    public void trim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }
    /**
     * 除集合中右边的元素，同时在左边加入一个元素。
     * @param key 键
     * @param key1 加入的元素
     * @return  返回右边的元素
     */
    @Override
    public Object rightPopAndLeftPush(String key, String key1) {
        return redisTemplate.opsForList().rightPopAndLeftPush(key, key1);
    }
    /*** 移除集合中右边的元素在等待的时间里，同时在左边添加元素，如果超过等待的时间仍没有元素则退出。
     * @param key 键
     * @param key1 左边增中的值
     * @param timeout 超时时间
     * @return 返回移除右边的元素
     */
    @Override
    public Object rightPopAndLeftPush(String key, String key1, long timeout) {
        return redisTemplate.opsForList().rightPopAndLeftPush(key,key1,timeout,TimeUnit.MILLISECONDS);
    }
    /**
     * 删除
     * @param keys 键
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
    /**
     * 设置过期时间
     * @param key 键
     * @param seconds 超时时间
     * @return true 成功 false 失败
     */
    @Override
    public boolean expire(String key, long seconds) {
        return redisTemplate.expire(key,seconds,TimeUnit.SECONDS);
    }
}
