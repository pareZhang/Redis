package com.zjm.service;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

import java.util.List;
import java.util.Set;

/**
 * @Author pareZhang
 * @Date 2020/5/5 12:34
 **/
public interface SetCacheService {
    boolean add(String key,Object...objects);

    boolean add(String key,int expireTime,Object...values);

    Set<Object> members(String key);

    long size(String key);

    boolean isMember(String key,Object o);

    boolean move(String key,Object value,String destValue);

    Object pop(String key);
    long remove(String key,Object...values);

    Cursor<Object> scan(String key, ScanOptions options);

    Set<Object> difference(String key, List list);
    Set<Object> difference(String key,String otherKeys);
    void differenceAndStore(String key,String key1,String destKey);
    void differenceAndStore(String key,List otherKeys,String destKey);

    Set<Object> interSect(String key,String key1);
    Set<Object> interSect(String key,List list);
    void intersectAndStore(String key,String key1,String destKey);
    void intersectAndStore(String key,List otherKeys,String destKey);

    Set<Object> union(String key,String key1);
    Set<Object> union(String key,Set set);
    void unionAndStore(String key,String key1,String destKey);
    void unionAndStore(String key,List list,String destKey);

    Object randomMember(String key);
    List<Object> randomMembers(String key,long count);
    Set<Object> distinctRandomMembers(String key,long count);

    void del(String...keys);
    boolean expire(String key, long seconds);

}
