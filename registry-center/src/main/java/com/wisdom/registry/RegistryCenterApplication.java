package com.wisdom.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RefreshScope
@EnableAutoConfiguration
@EnableEurekaServer
public class RegistryCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistryCenterApplication.class, args);
	}

}
