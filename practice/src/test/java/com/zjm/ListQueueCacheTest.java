package com.zjm;

import com.zjm.service.impl.ListCacheQueueServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author pareZhang
 * @Date 2020/5/3 19:15
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ListQueueCacheTest {
    @Autowired
    private ListCacheQueueServiceImpl listCacheQueueService;

    @Test
    public void t1() {
        String orderId = "100001"; //18位（为什么是18位的订单编号）
        //用户完成支付----->生成物流任务队列
        // listCacheQueueService.orderQueue(orderId);

        System.out.println("----->需要执行的物流任务列表----->执行----->");

        List<String> list = listCacheQueueService.orderSelect(orderId);
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println("----->快递小哥，消费该任务----->");
        String succQueue = listCacheQueueService.orderTouch(orderId);
        System.out.println("消费的是：" + succQueue);

        System.out.println("----->物流公司要看：当前需要执行的物流列表----->");

        List<String> list1 = listCacheQueueService.orderSelect(orderId);
        for (String s:list1) {
            System.out.println(s);
        }

        System.out.println("----->普通客户：我的货到哪了？----->");
        List<String> list2 = listCacheQueueService.orderSelectSucc(orderId);
        for (String s:list2){
            System.out.println(s);
        }
    }
}
