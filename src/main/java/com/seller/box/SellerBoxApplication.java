package com.seller.box;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SellerBoxApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		//System.setProperty("server.servlet.context-path", "/sellerbox");
		SpringApplication.run(SellerBoxApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SellerBoxApplication.class);
	}

}
