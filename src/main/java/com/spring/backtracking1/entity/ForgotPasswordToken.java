package com.spring.backtracking1.entity;
import java.sql.Date;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordToken {

	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private int token_id;
	
	@NonNull
	private long createdTime;
	
	@NonNull
	private String token;

    @OneToOne(targetEntity = UserData.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable=false, name="user_id", referencedColumnName="id")
	private UserData User;
	
	
}
