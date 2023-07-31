package com.spring.backtracking1.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import com.spring.backtracking1.service.BuySellStockService;

@RestController
public class BuySellStockController {
	
	
	@Autowired
	private BuySellStockService buysellService;
	
	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/buy")
	public ResponseEntity<String> buyStock(Authentication authentication,@RequestParam String symbol, @RequestParam double price,@RequestParam int quantity) {
		
		return ResponseEntity.ok(buysellService.buyStock(authentication,symbol,price,quantity));
		
		
}
	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/sell")
	public ResponseEntity<String> sellStock(Authentication authentication,@RequestParam String symbol, @RequestParam int quantity) throws RestClientException, IOException, InterruptedException {
		 	
		return ResponseEntity.ok(buysellService.sellStock(authentication,symbol,quantity));
	
	}
	
	 @PreAuthorize("hasAuthority('USER')")
	 
	 @GetMapping("/orders") 
	 public ResponseEntity<String> orders(Authentication authentication) throws RestClientException, IOException, InterruptedException
	 {
	 
	 return ResponseEntity.ok(buysellService.orders(authentication));
	 
	 }
	
}
