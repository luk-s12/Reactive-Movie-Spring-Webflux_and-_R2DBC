package com.example.demo.configs;

import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsConfig implements WebFluxConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("http://127.0.0.1:5500")
		.allowedMethods("POST", "PUT", "DELETE", "GET");
	}
	
}
