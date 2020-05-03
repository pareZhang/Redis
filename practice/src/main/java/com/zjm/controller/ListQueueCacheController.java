package com.zjm.controller;

import com.zjm.service.impl.ListCacheQueueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author pareZhang
 * @Date 2020/5/3 20:00
 **/
@RestController
public class ListQueueCacheController {
    @Autowired
    private ListCacheQueueServiceImpl listCacheQueueService;

    /**
     * 付款完成生成物流列表控制器
     * @param orderId
     */
    @GetMapping("/queueOrder/{orderId}")
    public void queueOrder(@PathVariable(value = "orderId") String orderId){
        listCacheQueueService.orderQueue(orderId);
    }

    /**
     * 快递正在消费的物流任务控制器
     * @param orderId
     * @return
     */
    @GetMapping("/orderTouch/{orderId}")
    public String orderTouch(@PathVariable(value = "orderId") String orderId){
        return listCacheQueueService.orderTouch(orderId);
    }

    /**
     * 快递公司需要执行的物流列表控制器
     * @param orderId
     * @return
     */
    @GetMapping("/orderSelect/{orderId}")
    public List<String> orderSelect(@PathVariable(value = "orderId") String orderId){
        return listCacheQueueService.orderSelect(orderId);
    }

    /**
     * 普通用户查询物流控制器
     * @param orderId
     * @return
     */
    @GetMapping("/orderSelectSucc/{orderId}")
    public List<String> orderSelectSucc(@PathVariable(value = "orderId") String orderId){
        return listCacheQueueService.orderSelectSucc(orderId);
    }
}
