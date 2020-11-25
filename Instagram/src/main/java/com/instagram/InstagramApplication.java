package com.instagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;

@EnableAsync
@SpringBootApplication
@EnableScheduling
public class InstagramApplication{

	/*@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations("classpath:/images/").setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	}
	*/

	public static void main(String[] args) {
		SpringApplication.run(InstagramApplication.class, args);
	}

	@Bean
	public Docket swaggerConfig() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/api/**"))
				.apis(RequestHandlerSelectors.basePackage("com.instagram"))
				.build()
				.apiInfo(apiInformation());
	}

	private ApiInfo apiInformation(){
		return new ApiInfo(
				"Instagram",
				"API for Instagram clone",
				"1.0",
				"Free to use",
				new springfox.documentation.service.Contact("Instgarm", "http://localhost:8080", "instagrama377@gmail.com"),
				"API License",
				"http://localhost:8080",
				Collections.emptyList());
	}
}

