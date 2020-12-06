package com.pierciccio.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCaching
@EnableFeignClients()
public class ArticoliWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticoliWebServiceApplication.class, args);
	}

}
