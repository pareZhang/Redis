package com.zjm.service.impl;

import com.zjm.service.LoginService;

import java.util.Map;

/**
 * @Author pareZhang
 * @Date 2020/4/20 11:17
 **/
public class LoginServiceImpl implements LoginService {
    /**
     *  1.判断当前登录的用户是否被限制登录
     *       查询当前KEY是否存在，如果存在，就被限制 注意：需要给用户做提示：您当前的用户已经被限制，还剩多长时间
     *       如果不存在，就不被限制
     * @param name
     * @return
     */
    @Override
    public Map<String, Object> loginUserLock(String name) {
        return null;
    }

    /**
     * 登录不成功相应操作
     * @param name
     * @return
     */
    @Override
    public String loginValdate(String name) {
        return null;
    }
}
