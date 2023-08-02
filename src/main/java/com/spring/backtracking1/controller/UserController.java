package com.spring.backtracking1.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.backtracking1.auth.JwtUtil;
import com.spring.backtracking1.authobject.LoginRequest;
import com.spring.backtracking1.authobject.LoginResponse;
import com.spring.backtracking1.entity.Password;
import com.spring.backtracking1.entity.UserData;
import com.spring.backtracking1.service.EmailService;
import com.spring.backtracking1.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.validation.Valid;

@RestController
public class UserController {
	@Autowired
	UserService userservice;

	@Autowired
	EmailService emailservice;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtutil;

	 Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/adminsignup")
	public ResponseEntity<String> createAdmin(@Valid @RequestBody UserData userData, @RequestParam String token ){
		String result = userservice.createAdmin(userData,token);
		return ResponseEntity.ok(result);		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<String> createUser(@Valid @RequestBody UserData userdata) {
		Boolean isNewRegistration = userservice.addUser(userdata);
		if (isNewRegistration) {
			return ResponseEntity.ok("USER_CREATED");
		} else {
			return ResponseEntity.ok("USER_ALREADY_EXISTS");
		}
	}
	
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest authRequest) {
		LoginResponse response = null;
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtutil.generateJwtToken(userDetails.getUsername());
            List<String> roles = userDetails.getAuthorities().stream().map(list -> list.getAuthority()).toList();
            response = new LoginResponse(token , jwtutil.getUserNameFromToken(token), roles);
            logger.info("User has been logged in with the user email {}",userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

    }

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotpassword(@RequestParam String email) {
		if (Boolean.TRUE.equals(userservice.forgotpwd(email))) {
			//System.out.println("CHECK_INBOX");
			return ResponseEntity.ok(emailservice.sendSimpleMail(email));
		}
		return ResponseEntity.ok("NO_USER_FOUND_WITH_EMAIL");
	}

	@PutMapping("/reset-password")
	public ResponseEntity<String> resetpwd(@Valid @RequestBody Password pwdDto) {
		if (userservice.resetToNewPwd(pwdDto)) {
			return ResponseEntity.ok("PASSWORD_RESET_DONE");
		} else {
			return ResponseEntity.ok("RETRY_AGAIN");
		}
	}
}
