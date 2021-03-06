package com.wisdom.search;

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
@MapperScan(basePackages = {"com.wisdom.search.mapper"})
class SearchServiceApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(SearchServiceApplication.class, args);
		SpringUtil.setApplicationContext(app);
	}

}
