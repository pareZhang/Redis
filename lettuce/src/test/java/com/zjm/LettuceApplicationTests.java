package com.zjm;

import com.zjm.po.User;
import com.zjm.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LettuceApplicationTests {
	@Autowired
	private UserServiceImpl userService;

	@Test
	public void contextLoads() {
		userService.getString("aa");
	}

	@Test
	public void t(){
		String result=userService.getString("redisStr");
		System.out.println(result);
	}
	@Test
	public void t2(){
		userService.expireStr("test","测试数据有效期");
		System.out.println("操作成功!");
	}
	@Test
	public void t3(){
		User user=userService.selectById("10005");
		System.out.println("查询的结果："+user);
	}

}
