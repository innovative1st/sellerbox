package com.seller.box;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("server.servlet.context-path", "/sellerbox");
		builder.application().setDefaultProperties(map);
		return builder.sources(SellerBoxApplication.class);
	}

}