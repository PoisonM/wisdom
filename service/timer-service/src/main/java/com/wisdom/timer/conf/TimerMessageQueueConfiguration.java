/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wisdom.timer.conf;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jedis Cache 工具类
 * 
 * @author ThinkGem
 * @version 2014-6-29
 */

@Configuration
public class TimerMessageQueueConfiguration {

	@Bean
	public Queue confirmReceiveProductQueue() {
		return new Queue("confirmReceiveProduct");
	}

	@Bean
	public Queue deFrozenUserReturnMoneyQueue() {
		return new Queue("deFrozenUserReturnMoney");
	}

	@Bean
	public Queue promoteUserBusinessTypeForRecommendQueue() {
		return new Queue("promoteUserBusinessTypeForRecommend");
	}

	@Bean
	public Queue frozenUserTypeQueue() {
		return new Queue("frozenUserType");
	}

}
