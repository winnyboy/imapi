package com.huione.im.feign;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.huione.im.api.ServiceFeignApplication;
import com.huione.im.api.service.GroupService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServiceFeignApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceFeignApplicationTests {
	
	@Test
	public void contextLoads() {
	}

}
