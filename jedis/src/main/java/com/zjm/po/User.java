package com.zjm.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author pareZhang
 * @Date 2020/4/7 22:51
 **/
@Data
public class User implements Serializable {
    private String id;
    private String name;
    private Integer age;
}
