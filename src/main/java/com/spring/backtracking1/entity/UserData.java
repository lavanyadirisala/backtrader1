package com.spring.backtracking1.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class UserData implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;	
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
		
	@Email(message = "Please provide a valid e-mail")
	@Column(name = "Email",nullable = false)
	@NotBlank(message = "Email is mandatory")
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	    @JoinTable(name="user_roles",
	    joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
	    inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")})
		private List<Roles>roles=new ArrayList<>();
}