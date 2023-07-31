package com.spring.backtracking1.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.lang.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.backtracking1.auth.JwtUtil;
import com.spring.backtracking1.repository.StockDataWithSymbolRepository;
import com.spring.backtracking1.repository.StockRespository;
import com.spring.backtracking1.repository.UserRepository;
import com.spring.backtracking1.entity.MetaData;
import com.spring.backtracking1.entity.Stock;
import com.spring.backtracking1.entity.StockData;
import com.spring.backtracking1.entity.StockDataWithSymbol;
import com.spring.backtracking1.entity.StockPrice;
import com.spring.backtracking1.entity.UserData;
import com.google.gson.Gson;

@Service
public class FinanceService {
	@Autowired
	private StockRespository stockRespository;
	@Autowired
	private StockDataWithSymbolRepository stockDataWithSymbolRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FinanceAPICall financeAPICall;
	@Autowired
	private JwtUtil jwtutil;
	private static final DecimalFormat decimalformat = new DecimalFormat("0.00");

	final int MAX_SIZE = 31;
	
	public List<StockDataWithSymbol> getStockPrice(String token, String symbol, String function) {
		List<String> information = new ArrayList<>(List.of("Intraday (5) open, high, low, close prices and volume",
				"Daily Time Series with Splits and Dividend Events",
				"Weekly Prices (open, high, low, close) and Volumes",
				"Monthly Prices (open, high, low, close) and Volumes"));
		token = token.substring(7);
		String username = jwtutil.getUserNameFromToken(token);
		String json = financeAPICall.getStockPrice(symbol, function);
		Gson gson = new Gson();
		StockPrice stockPrice = gson.fromJson(json, StockPrice.class);
		Stock stock = gson.fromJson(json, Stock.class);
		stock.setDate(new Date());
		stock.setNode(json);
		stock.setSymbol(symbol);
		MetaData metaData = stockPrice.getMetaData();
		StockData stockData = null;
		UserData users = userRepository.findByEmail(username);
		if (users != null) {
			stock.setUserid(users);
			if (function.equals("TIME_SERIES_MONTHLY")) {
				stockRespository.save(stock);
				List<StockDataWithSymbol> stockDataWithSymbolList = stockDataWithSymbolRepository
						.findByInformationAndSymbol(information.get(3), symbol);
				if (stockDataWithSymbolList.isEmpty()) {
					int size = 0;
					List<StockDataWithSymbol> stockdatalist = new ArrayList<>();
					for (Map.Entry<String, StockData> entry : stockPrice.getStockMonthly().entrySet()) {
						String date = entry.getKey();
						stockData = entry.getValue();
						StockDataWithSymbol dataWithSymbol = new StockDataWithSymbol();
						dataWithSymbol.setSymbol(metaData.getSymbol());
						dataWithSymbol.setDate(date);
						dataWithSymbol.setHighPrice(decimalformat.format(Double.valueOf(stockData.getHigh())));
						dataWithSymbol.setLowPrice(decimalformat.format(Double.valueOf(stockData.getLow())));
						dataWithSymbol.setClosedPrice(decimalformat.format(Double.valueOf(stockData.getClose())));
						dataWithSymbol.setOpenPrice(decimalformat.format(Double.valueOf(stockData.getOpen())));
						dataWithSymbol.setUsers(users);
						dataWithSymbol.setInformation(metaData.getInformation());
						size++;
						if (size >= MAX_SIZE)
							break;
						stockdatalist.add(dataWithSymbol);
					}
					return stockDataWithSymbolRepository.saveAll(stockdatalist);

				} else {
					int size = 0;
					StockDataWithSymbol stockValues = new StockDataWithSymbol();
					for (Map.Entry<String, StockData> entry : stockPrice.getStockMonthly().entrySet()) {
						++size;
						if (size >=MAX_SIZE)
							break;
						String date = entry.getKey();
						stockData = entry.getValue();
						stockValues.setSymbol(symbol);
						stockValues.setHighPrice(decimalformat.format(Double.valueOf(stockData.getHigh())));
						stockValues.setLowPrice(decimalformat.format(Double.valueOf(stockData.getLow())));
						stockValues.setClosedPrice(decimalformat.format(Double.valueOf(stockData.getClose())));
						stockValues.setDate(date);
						stockValues.setOpenPrice(decimalformat.format(Double.valueOf(stockData.getOpen())));
						stockValues.setId(stockDataWithSymbolList.get(size - 1).getId());
						stockValues.setInformation(metaData.getInformation());
						stockValues.setUsers(users);

						stockDataWithSymbolRepository.save(stockValues);
					}
					return stockDataWithSymbolRepository.findAll();
				}

			} else if (function.equals("TIME_SERIES_WEEKLY")) {
				stockRespository.save(stock);
				List<StockDataWithSymbol> stockDataWithSymbolList = stockDataWithSymbolRepository
						.findByInformationAndSymbol(information.get(2), symbol);
				if (stockDataWithSymbolList.isEmpty()) {
					int size = 0;
					List<StockDataWithSymbol> stockdatalist = new ArrayList<>();
					for (Map.Entry<String, StockData> entry : stockPrice.getStockweekly().entrySet()) {

						String date = entry.getKey();
						stockData = entry.getValue();
						StockDataWithSymbol dataWithSymbol = new StockDataWithSymbol();
						dataWithSymbol.setSymbol(metaData.getSymbol());
						dataWithSymbol.setDate(date);
						dataWithSymbol.setHighPrice(decimalformat.format(Double.valueOf(stockData.getHigh())));
						dataWithSymbol.setLowPrice(decimalformat.format(Double.valueOf(stockData.getLow())));
						dataWithSymbol.setClosedPrice(decimalformat.format(Double.valueOf(stockData.getClose())));
						dataWithSymbol.setOpenPrice(decimalformat.format(Double.valueOf(stockData.getOpen())));
						dataWithSymbol.setId(stockDataWithSymbolList.get(size - 1).getId());
						dataWithSymbol.setInformation(metaData.getInformation());
						size++;
						if (size >= MAX_SIZE)
							break;
						stockdatalist.add(dataWithSymbol);
					}
					return stockDataWithSymbolRepository.saveAll(stockdatalist);

				} else {
					int size = 0;
					StockDataWithSymbol stockValues = new StockDataWithSymbol();
					for (Map.Entry<String, StockData> entry : stockPrice.getStockweekly().entrySet()) {
						++size;
						if (size >= MAX_SIZE)
							break;
						String date = entry.getKey();
						stockData = entry.getValue();
						stockValues.setSymbol(symbol);
						stockValues.setHighPrice(decimalformat.format(Double.valueOf(stockData.getHigh())));
						stockValues.setLowPrice(decimalformat.format(Double.valueOf(stockData.getLow())));
						stockValues.setClosedPrice(decimalformat.format(Double.valueOf(stockData.getClose())));
						stockValues.setDate(date);
						stockValues.setOpenPrice(decimalformat.format(Double.valueOf(stockData.getOpen())));
						stockValues.setInformation(metaData.getInformation());
						stockValues.setUsers(users);

						stockDataWithSymbolRepository.save(stockValues);
					}
					return stockDataWithSymbolRepository.findAll();
				}
			} else if (function.equals("TIME_SERIES_DAILY_ADJUSTED")) {
				stockRespository.save(stock);
				List<StockDataWithSymbol> stockDataWithSymbolList = stockDataWithSymbolRepository
						.findByInformationAndSymbol(information.get(1), symbol);
				if (stockDataWithSymbolList.isEmpty()) {
					int size = 0;
					List<StockDataWithSymbol> stockdatalist = new ArrayList<>();
					for (Map.Entry<String, StockData> entry : stockPrice.getStockdaily().entrySet()) {

						String date = entry.getKey();
						stockData = entry.getValue();
						StockDataWithSymbol dataWithSymbol = new StockDataWithSymbol();
						dataWithSymbol.setSymbol(metaData.getSymbol());
						dataWithSymbol.setDate(date);	
						dataWithSymbol.setHighPrice(decimalformat.format(Double.valueOf(stockData.getHigh())));
						dataWithSymbol.setLowPrice(decimalformat.format(Double.valueOf(stockData.getLow())));
						dataWithSymbol.setClosedPrice(decimalformat.format(Double.valueOf(stockData.getClose())));
						dataWithSymbol.setOpenPrice(decimalformat.format(Double.valueOf(stockData.getOpen())));
						dataWithSymbol.setInformation(metaData.getInformation());
						size++;
						if (size >= MAX_SIZE)
							break;
						stockdatalist.add(dataWithSymbol);
					}
					return stockDataWithSymbolRepository.saveAll(stockdatalist);

				} else {
					int size = 0;
					StockDataWithSymbol stockValues = new StockDataWithSymbol();
					for (Map.Entry<String, StockData> entry : stockPrice.getStockdaily().entrySet()) {
						++size;
						if (size >= MAX_SIZE)
							break;
						String date = entry.getKey();
						stockData = entry.getValue();

						stockValues.setSymbol(symbol);
						stockValues.setHighPrice(decimalformat.format(Double.valueOf(stockData.getHigh())));
						stockValues.setLowPrice(decimalformat.format(Double.valueOf(stockData.getLow())));
						stockValues.setClosedPrice(decimalformat.format(Double.valueOf(stockData.getClose())));
						stockValues.setDate(date);
						stockValues.setOpenPrice(decimalformat.format(Double.valueOf(stockData.getOpen())));
						stockValues.setId(stockDataWithSymbolList.get(size - 1).getId());
						stockValues.setInformation(metaData.getInformation());
						stockValues.setUsers(users);

						stockDataWithSymbolRepository.save(stockValues);
					}
					return stockDataWithSymbolRepository.findAll();
				}
			} else if (function.equals("TIME_SERIES_INTRADAY")) {
				stockRespository.save(stock);
				List<StockDataWithSymbol> stockDataWithSymbolList = stockDataWithSymbolRepository
						.findByInformationAndSymbol(information.get(0), symbol);
				if (stockDataWithSymbolList.isEmpty()) {
					int size = 0;
					List<StockDataWithSymbol> stockdatalist = new ArrayList<>();
					for (Map.Entry<String, StockData> entry : stockPrice.getStockIntraDay().entrySet()) {
						String date = entry.getKey();
						stockData = entry.getValue();
						StockDataWithSymbol dataWithSymbol = new StockDataWithSymbol();
						dataWithSymbol.setSymbol(metaData.getSymbol());
						dataWithSymbol.setDate(date);	
						dataWithSymbol.setHighPrice(decimalformat.format(Double.valueOf(stockData.getHigh())));
						dataWithSymbol.setLowPrice(decimalformat.format(Double.valueOf(stockData.getLow())));
						dataWithSymbol.setClosedPrice(decimalformat.format(Double.valueOf(stockData.getClose())));
						dataWithSymbol.setOpenPrice(decimalformat.format(Double.valueOf(stockData.getOpen())));
						dataWithSymbol.setInformation(metaData.getInformation());
						size++;
						if (size >= MAX_SIZE)
							break;
						stockdatalist.add(dataWithSymbol);
					}
					return stockDataWithSymbolRepository.saveAll(stockdatalist);

				} else {
					int size = 0;
					StockDataWithSymbol stockValues = new StockDataWithSymbol();
					for (Map.Entry<String, StockData> entry : stockPrice.getStockIntraDay().entrySet()) {
						++size;
						if (size >= MAX_SIZE)
							break;
						String date = entry.getKey();
						stockData = entry.getValue();
						stockValues.setSymbol(symbol);stockValues.setHighPrice(decimalformat.format(Double.valueOf(stockData.getHigh())));
						stockValues.setLowPrice(decimalformat.format(Double.valueOf(stockData.getLow())));
						stockValues.setClosedPrice(decimalformat.format(Double.valueOf(stockData.getClose())));
						stockValues.setDate(date);
						stockValues.setOpenPrice(decimalformat.format(Double.valueOf(stockData.getOpen())));
						stockValues.setId(stockDataWithSymbolList.get(size - 1).getId());
						stockValues.setInformation(metaData.getInformation());
						stockValues.setUsers(users);
						stockDataWithSymbolRepository.save(stockValues);
					}
					return stockDataWithSymbolRepository.findAll();
				}
			}

		}

		return Collections.emptyList();
	}

}
