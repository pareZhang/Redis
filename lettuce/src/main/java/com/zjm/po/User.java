package com.zjm.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author pareZhang
 * @Date 2020/4/13 11:26
 **/
@Data
public class User implements Serializable {
    private String id;
    private String name;
    private Integer age;

    public static String getKeyName(){
        return "user";
    }
}
