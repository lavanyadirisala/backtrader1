package com.spring.backtracking1.scheduler;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.spring.backtracking1.repository.StockDataWithSymbolRepository;
import com.spring.backtracking1.repository.UserRepository;
import com.spring.backtracking1.entity.StockDataWithSymbol;
import com.spring.backtracking1.entity.UserData;

@Service
public class SchedulerForFinanceData {
	Logger logger = LoggerFactory.getLogger(SchedulerForFinanceData.class);
	@Autowired
	private StockDataWithSymbolRepository stockDataWithSymbolRepository;
	@Autowired
	private UserRepository userRepository;

	@Scheduled(fixedRate = 300000)
	public List<StockDataWithSymbol> findAll() {
		logger.info("scheduler data called");
		return stockDataWithSymbolRepository.findAll();
	}

	public List<StockDataWithSymbol> findStock(Authentication authentication, String symbol) {
		logger.info("findstock method initiated");
		UserData users = userRepository.findByEmail(authentication.getName());
		if(users!=null) {
		logger.info("find Stock data got called");
		return stockDataWithSymbolRepository.findBySymbol(symbol);
		}
		else {
			return null;
		}
	}
}
