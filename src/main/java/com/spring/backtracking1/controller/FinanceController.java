package com.spring.backtracking1.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spring.backtracking1.entity.StockDataWithSymbol;
import com.spring.backtracking1.service.FinanceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@SecurityRequirement(name = "Authorization")
public class FinanceController {

	Logger logger = LoggerFactory.getLogger(FinanceController.class);
	@Autowired
	private FinanceService financeService;

	@GetMapping(value = "/getstock")
	public ResponseEntity<List<StockDataWithSymbol>> get(@RequestHeader("Authorization") String token, @RequestParam String symbol,
			@RequestParam String function){
		System.out.print(token);
		logger.info("FINANCE METHOD GOT CALLED");
		logger.info("GETTING STOCK DATA FOR SYMBOL {} FUNCTION {}" , symbol, function);
		var stockPriceList = financeService.getStockPrice(token, symbol, function);
		return new ResponseEntity<>(stockPriceList, HttpStatus.OK);

	}
}
