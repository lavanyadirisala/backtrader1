package com.spring.backtracking1.entity;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StockData {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	@SerializedName("1. open")
	private String open;

	@SerializedName("2. high")
	private String high;

	@SerializedName("3. low")
	private String low;

	@SerializedName("4. close")
	private String close;

	@SerializedName("5. adjusted close")
	private String adjustedClose;

	/*
	 * @SerializedName("6. volume") private String volume;
	 * 
	 * @SerializedName("7. dividend amount") private String dividendAmount;
	 * 
	 * @SerializedName("8. split coefficient") private String splitCoefficient;
	 */
	private String date;
	 
	@ManyToOne(targetEntity = MetaData.class,cascade = CascadeType.ALL)
	private MetaData metaData;
}
