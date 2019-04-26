package com.seller.box.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.seller.box.controller.InventoryController;
import com.seller.box.controller.OrderCancellationController;
import com.seller.box.controller.OrderController;
import com.seller.box.controller.OrderReturnsController;
import com.seller.box.controller.ProductController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource("classpath:swagger.yaml")
@ComponentScan(basePackageClasses = {OrderController.class, OrderCancellationController.class,
		 OrderReturnsController.class, ProductController.class, InventoryController.class})
public class SwaggerConfig extends WebMvcConfigurationSupport {
	@Value("${swagger.info.version}")
	private String version;
	@Bean
    public Docket sellerboxApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.seller.box.controller"))
                .paths(regex("/*.*"))
                .build()
                .apiInfo(apiInfo());
    }

	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Seller Box")
                .description("REST API for Seller Box")
                .version(version)
                //.license("Apache License Version 2.0")
                //.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                //.contact(new Contact("Md.Murad Hussain", "http://sellerbox.com/about/", "mahikhan001@yahoo.co.uk"))
                .build();
    }
	
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
