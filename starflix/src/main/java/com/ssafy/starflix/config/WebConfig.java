package com.ssafy.starflix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.starflix.util.JWTUtil;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private JwtInterceptor jwtInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
			.addPathPatterns("/user/**")
			.addPathPatterns("/board/**", "/comments/**")
			.addPathPatterns("/starPlace/**")
			.excludePathPatterns("/user/login", "/user/logout", "/user")
			.excludePathPatterns("/board", "/comments/list/{bno}")
			.excludePathPatterns("/starPlaces", "/starPlace/{idx}/travels", "/starPlace/{idx}/camping");
	}
}
