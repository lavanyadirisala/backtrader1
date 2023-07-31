package com.spring.backtracking1.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.backtracking1.entity.Stock;
import com.spring.backtracking1.entity.UserData;

public interface StockRespository extends JpaRepository<Stock, Integer> {
	public List<Stock> findByUserid(UserData users);
	public List<Stock> findBySymbol(String symbol);
}
