package com.viswagopi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EmpcardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpcardServiceApplication.class, args);
	}

}
