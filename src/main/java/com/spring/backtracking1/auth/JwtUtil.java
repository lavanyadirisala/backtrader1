package com.spring.backtracking1.auth;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.spring.backtracking1.entity.UserData;
import com.spring.backtracking1.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component

public class JwtUtil {

    long expDate = 30000;

    @Value("${app.jwtSecret}")

    private String key;

    @Autowired
    @Lazy
    private UserService userService;

     public String generateJwtToken(String email) {

        UserDetails details = userService.loadUserByUsername(email);
        UserData  users = userService.findByEmail(email);

        return Jwts.builder().setSubject(details.getUsername()).setIssuedAt(new Date()).claim("phone", users.getPhone())

                .claim("firstname", users.getFirstname())

                .claim("lastname", users.getLastname())

                .claim("roles", users.getRoles())

                .signWith(SignatureAlgorithm.HS256, key.getBytes()).compact();

    }

 

    public String getUserNameFromToken(String token) {

        return Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody().getSubject();

    }

 

    public boolean isTokenExpired(String authToken) {

        return Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(authToken).getBody().getExpiration()

                .before(new Date(System.currentTimeMillis()));

    }

 

    public boolean validateJwtToken(String authToken) {

        return (getUserNameFromToken(authToken) != null);

    }

 

}
