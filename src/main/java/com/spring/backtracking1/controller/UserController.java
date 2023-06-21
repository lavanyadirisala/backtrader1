package com.spring.backtracking1.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.spring.backtracking1.auth.JwtUtil;
import com.spring.backtracking1.authobject.LoginRequest;
import com.spring.backtracking1.authobject.LoginResponse;
import com.spring.backtracking1.entity.Password;
import com.spring.backtracking1.entity.UserData;
import com.spring.backtracking1.service.EmailService;
import com.spring.backtracking1.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userservice;

	@Autowired
	EmailService emailservice;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtutil;

	@PostMapping("/register")
	public ResponseEntity<String> createUser(@Valid @RequestBody UserData userdata) {
		UserData user = (UserData) userservice.addUser(userdata);
		if (user != null) {
			return ResponseEntity.ok("USER_CREATED");
		} else {
			return ResponseEntity.ok("USER_ALREADY_EXISTS");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
		LoginResponse response = null;
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		// create JWT token
		String token = jwtutil.generateJwtToken(authentication);
		List<String> roles = userDetails.getAuthorities().stream().map(list -> list.getAuthority()).toList();
		response = new LoginResponse(token, roles, userDetails.getUsername());
		return ResponseEntity.ok("LOGIN_SUCCESSFUL");
	}

	@PostMapping("/forgotpassword")
	public ResponseEntity<String> forgotpassword(@RequestParam String email) {
		if (Boolean.TRUE.equals(userservice.forgotpwd(email))) {
			System.out.println("CHECK_INBOX");
			return ResponseEntity.ok(emailservice.sendSimpleMail(email));
		}
		return ResponseEntity.ok("NO_USER_FOUND_WITH_EMAIL");
	}

	@PutMapping("/resetpassword")
	public ResponseEntity<String> resetpwd(@Valid @RequestBody Password pwdDto) {
		if (userservice.resetToNewPwd(pwdDto)) {
			return ResponseEntity.ok("PASSWORD_RESET_DONE");
		} else {
			return ResponseEntity.ok("RETRY_AGAIN");
		}
	}
}
