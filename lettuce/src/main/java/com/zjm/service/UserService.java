package com.zjm.service;

import com.zjm.po.User;

/**
 * @Author pareZhang
 * @Date 2020/4/18 17:59
 **/
public interface UserService {
    /**
     * Redis有什么命令，Jedis就有什么方法
     * Redis 的 String类型
     * 需求：用户输入一个key，先判断redis中是否存在该数据，如果存在，再redis中进行查询,并返回，如果不存在，在MYSQL中查询，
     * 将结果赋值给Redis，并返回
     * @param key
     * @return
     */
    public String getString(String key);
    /**
     * 测试String类型
     * 需求：用户输入一个redis数据，该key的有效期为28小时
     */
    public void expireStr(String key,String value);

    /**
     * 测试hash类型 演示
     * @param id
     * @return
     */
    public User selectById(String id);
}
