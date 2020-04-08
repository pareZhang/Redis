package com.zjm;

import com.zjm.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisPool;

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


}
