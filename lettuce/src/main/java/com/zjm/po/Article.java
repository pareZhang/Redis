package com.zjm.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author pareZhang
 * @Date 2020/5/1 21:54
 **/
@Data
public class Article implements Serializable {
    /**主键ID**/
    private String id;
    /**文章标题**/
    private String title;
    /**正文内容**/
    private String content;
    /**作者**/
    private String author;
    /**发布时间**/
    private String createDate;
    /**点击量 or 阅读量**/
    private Integer clickNum;
}
