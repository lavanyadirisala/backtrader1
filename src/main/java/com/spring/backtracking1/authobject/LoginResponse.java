package com.spring.backtracking1.authobject;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
	String jwtToken;
	String email;
	List<String> roles;

}
