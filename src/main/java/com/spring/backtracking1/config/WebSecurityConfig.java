package com.spring.backtracking1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.spring.backtracking1.auth.AuthJwtTokenFilter;
import com.spring.backtracking1.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
	
	@Autowired
	UserService service;
	
	@Autowired
	private AuthJwtTokenFilter filter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		security.csrf(key->key.disable())
				.authorizeHttpRequests(request -> request.requestMatchers("/adminsignup","/findAll","/login", "/signup","/forgot-password","/reset-password","/getstock","/buy","/sell","/v3/api-docs/**", "/swagger-ui/**")
						.permitAll().anyRequest().authenticated())
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return security.build();
	}
	  
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	  @Bean 
	  public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception 
	  { 
		  AuthenticationManagerBuilder managerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
		  managerBuilder.userDetailsService(service).passwordEncoder(passwordEncoder());
		  return managerBuilder.build(); 
	   }
	 
}
