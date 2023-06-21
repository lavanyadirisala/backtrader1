package com.spring.backtracking1.auth;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Value("${app.jwtSecret}")
	private  String jwtSecret;
	@Value("${app.jwtExpiration}")
	private  int jwtExpiration;

	final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	public String generateJwtToken(Authentication authentication) {		 		  
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		
		/*
		 * Set<String> authorities = userPrincipal.getAuthorities().stream()
		 * .map(GrantedAuthority::getAuthority) .collect(Collectors.toSet());
		 */
		 List<String> authorities = userPrincipal.getAuthorities().stream().map(list -> list.getAuthority()).toList();
			
		return Jwts.builder().setSubject(userPrincipal.getUsername()).claim("roles", authorities).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (Exception e) {
			logger.warn("Exception - INVALID JWT TOKEN");
		}
		return false;
	}

}
