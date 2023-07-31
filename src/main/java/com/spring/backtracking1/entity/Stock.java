package com.spring.backtracking1.entity;


import java.util.Date;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	@JdbcTypeCode(SqlTypes.JSON)
	private Object node;
	private Date date;
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = UserData.class)
	private UserData userid;
	private String symbol;
}
