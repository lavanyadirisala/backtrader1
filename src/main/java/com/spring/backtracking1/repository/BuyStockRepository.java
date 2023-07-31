package com.spring.backtracking1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.backtracking1.entity.BuyStock;
import com.spring.backtracking1.entity.UserData;

@Repository
public interface BuyStockRepository extends JpaRepository<BuyStock, Integer> {

	List<BuyStock> findByUser(UserData user);
	
	
	BuyStock findBySymbolAndUser(String symbol, UserData user);
	
	
	 
}
