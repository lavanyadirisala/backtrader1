package com.spring.backtracking1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.backtracking1.entity.ForgotPasswordToken;

public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordToken, Integer>{
	public ForgotPasswordToken findByToken(String token);
}
