package com.zjm.service;

/**
 * @Author pareZhang
 * @Date 2020/4/8 13:06
 **/
public interface UserService {
    /**
     * Redis 的 String类型
     * 需求：用户输入一个key，先判断redis中是否存在该数据，如果存在，再redis中进行查询,并返回，如果不存在，在MYSQL中查询，
     * 将结果赋值给Redis，并返回
     */
    public String getString(String key);

    /**
     * 测试String类型
     * 需求：用户输入一个redis数据，该key的有效期为28小时
     */
    public void expireStr(String key,String value);
}
