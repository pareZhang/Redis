package com.zjm;

import com.zjm.po.Article;
import com.zjm.service.impl.ListCacheServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author pareZhang
 * @Date 2020/5/1 22:33
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ListCacheTest {
    @Autowired
    private ListCacheServiceImpl listCacheService;
    @Test
    public void t1(){
        //1.初始化数据
        //listCacheService.initArticle();
        //2.查询
        System.out.println("----->打印最新的首页文章<-----");
        List<Article> list=listCacheService.selectArticleTop5();
        for (Article article:list) {
            System.out.println(article);
        }

        //3.新增
        System.out.println("----->小编添加了一个最新的文章<-----");
        Article article=new Article();//实际生产中，此数据从书库查询而来。此处是模拟。
        article.setId("22282");
        article.setTitle("联手抗疫，习主席打了哪些“国际长途”？");
        article.setContent("好好学习Redis");
        article.setAuthor("pareZhang");
        article.setClickNum(888);
        article.setCreateDate("2020-05-03 12:30");
        listCacheService.insertArticle(article);

        System.out.println("----->打印最新的首页文章5个<-----");
        List<Article> list1=listCacheService.selectArticleTop5();
        for (Article ar:list1) {
            System.out.println(ar);
        }

        //4.删除，只保留5条最新
        System.out.println("----->删除最新的5条之外的记录<-----");
        listCacheService.delArticle();
        System.out.println("----->最新的5条文章<-----");
        List<Article> list2=listCacheService.selectArticleTop5();
        for (Article ar:list2) {
            System.out.println(ar);
        }

    }
}
