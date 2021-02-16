package com.eatza.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.eatza.review.config.JwtFilter;

@EnableEurekaClient
@EnableFeignClients

@SpringBootApplication
public class ReviewserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewserviceApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean jwtFilterBean() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/review/*");

		return registrationBean;
	}
}
