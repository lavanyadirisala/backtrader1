package com.spring.backtracking1.entity;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Password {
	
	private String token;
	@Valid
	private String newPassword;
	private String confirmPassword;
}
