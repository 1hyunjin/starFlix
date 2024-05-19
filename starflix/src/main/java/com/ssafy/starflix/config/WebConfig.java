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
			.addPathPatterns("/users/**", "/boards/**", "/comments/**", "/star-places/**" , "/reviews/**", "/jjims/**") 
			.excludePathPatterns("/users/login", "/users/logout", "/users")
			.excludePathPatterns("/boards")
			.excludePathPatterns("/star-places/{idx}/travels", "/star-places/{idx}/camping");
	}
}
