package com.spring.backtracking1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.backtracking1.entity.BuyStock;
import com.spring.backtracking1.entity.SellStock;

@Repository
public interface SellStockRepository extends JpaRepository<SellStock,Integer>{
	/*
	 * @Query(value =
	 * "SELECT * FROM Sell_stock s  WHERE s.Global Quote->'05. price' ", nativeQuery
	 * = true) String findString();
	 */
}
