package com.spring.backtracking1.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.backtracking1.entity.ForgotPasswordToken;
import com.spring.backtracking1.entity.UserData;

@Repository
public interface UserRepository extends JpaRepository<UserData,Integer> {
	
	UserData findByEmail(String email);
	
	
}
