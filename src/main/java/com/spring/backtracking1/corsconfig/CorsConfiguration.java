package com.spring.backtracking1.corsconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration{

	@Bean
	public WebMvcConfigurer webmvc() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOriginPatterns("http://localhost:3000","http://localhost:3001")
						.allowedOrigins("http://localhost:3000","http://localhost:3001")
							.allowedMethods("GET","POST","PUT","DELETE","PATCH","OPTIONS")
								.allowedHeaders("*")
									.exposedHeaders("Authorization")
										.allowCredentials(true);
									
						
			}
		};
	}
	
	
}
