package com.ch.cloud;

import com.ch.cloud.client.UpmsClientService;
import com.ch.cloud.upms.UpmsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = UpmsApplication.class)
public class ClientTests {

//	@Autowired
	UpmsClientService upmsClientService;

	Object o;

//	@Test
	public void contextLoads() {

//		o = upmsClientService.findPermissionsByRoleId(0L);
		o = upmsClientService.findMenusByRoleId(0L);

		System.out.println(o);
	}

}

