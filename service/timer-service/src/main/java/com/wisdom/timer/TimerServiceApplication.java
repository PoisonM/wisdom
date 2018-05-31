package com.wisdom.timer;

import com.wisdom.timer.client.WeixinServiceClient;
import com.wisdom.timer.service.business.BusinessRunTimeService;
import com.wisdom.common.util.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.UnsupportedEncodingException;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableFeignClients
@MapperScan(basePackages = {"com.wisdom.timer.mapper"})
public class TimerServiceApplication {

	public static void main(String[] args) {

		ApplicationContext app = SpringApplication.run(TimerServiceApplication.class, args);
		SpringUtil.setApplicationContext(app);

		WeixinServiceClient weixinServiceClient = SpringUtil.getBean(WeixinServiceClient.class);
		BusinessRunTimeService businessRunTimeService = SpringUtil.getBean(BusinessRunTimeService.class);

		//初始化时更新token
		weixinServiceClient.updateUserWeixinToken();

//		weixinServiceClient.updateBeautyWeixinToken();
//
//		//初始化时处理未支付的订单
//		businessRunTimeService.autoProcessNoPayRecordData();

//		try {
//			businessRunTimeService.autoMonthlyIncomeCalc();
//			businessRunTimeService.autoProcessUserAccount();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
	}

}
