package com.zjm;

import com.sun.media.jfxmediaimpl.HostUtils;
import com.zjm.po.User;
import com.zjm.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JedisApplicationTests {

	@Autowired
	private UserService userService;
	@Test
	public void contextLoads() {

	}

	/**
	 * 模拟Jedis操作Redis 的String类型数据
	 */
	@Test
	public void t1(){
		String result=userService.getString("name");
		System.out.println(result);
	}

	/**
	 * 测试过期时间
	 */
	@Test
	public void t2(){
		String key="testKey";
		String value="测试数据";
		userService.expireStr(key,value);
	}
	@Test
	public void t3(){
		User user = userService.selectById("10002");
		System.out.println(user);

}


}
