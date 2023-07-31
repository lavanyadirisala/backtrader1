package com.spring.backtracking1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.backtracking1.entity.StockDataWithSymbol;

public interface StockDataWithSymbolRepository extends JpaRepository<StockDataWithSymbol, Integer> {

	public List<StockDataWithSymbol> findByInformationAndSymbol(String information, String symbol);

	public List<StockDataWithSymbol> findBySymbol(String symbol);
}
