package com.wisdom.weixin;

import com.wisdom.common.util.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableFeignClients
@MapperScan(basePackages = {"com.wisdom.weixin.mapper"})
public class WeixinServiceApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(WeixinServiceApplication.class, args);
		SpringUtil.setApplicationContext(app);
	}

}
