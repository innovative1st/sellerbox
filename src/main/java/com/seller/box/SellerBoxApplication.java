package com.seller.box;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class SellerBoxApplication extends ServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SellerBoxApplication.class, args);
	}

}
