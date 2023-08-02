package com.spring.backtracking1.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import org.springframework.web.client.RestTemplate;
import com.spring.backtracking1.entity.BuyStock;
import com.spring.backtracking1.entity.Order;
import com.spring.backtracking1.entity.SellStock;

import com.spring.backtracking1.entity.UserData;

import com.spring.backtracking1.repository.BuyStockRepository;
import com.spring.backtracking1.repository.OrdersRepository;
import com.spring.backtracking1.repository.SellStockRepository;

import com.spring.backtracking1.repository.UserRepository;

@Service

public class BuySellStockService {

	@Value("${alphavantage.api.key}")

	String apikey;

	@Autowired
	private SellStockRepository sellRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BuyStockRepository buyRepo;

	@Autowired
	private OrdersRepository orderRepo;
	

	private static final DecimalFormat decimalformat = new DecimalFormat("0.00");
	Logger logger = org.slf4j.LoggerFactory.getLogger(BuySellStockService.class);
	private final RestTemplate restTemplate = new RestTemplate();
	Date currentDate = new Date(System.currentTimeMillis());
	
	public String symbolData(String symbol, int quantity)
			throws IOException, InterruptedException, RestClientException {

		String apiUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE" + "&symbol=" + symbol + "&apikey="
				+ apikey;
		ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
		String json = response.getBody();
		return json;

	}

	public String getPricefromJSON(String jsonData) {
		JSONObject jsonObject = new JSONObject(jsonData);
		String price = jsonObject.getJSONObject("Global Quote").getString("05. price");
		System.out.println(price);
		return price;

	}

	public ResponseEntity<String> sellStock(Authentication authentication, String symbol, int quantity)
			throws RestClientException, IOException, InterruptedException {
		UserData userData = userRepo.findByEmail(authentication.getName());
		logger.info("USER {} IS SELLING STOCKS OF {}  OF QUANTITY {} ", userData.getId(), symbol,  quantity);
		Order newOrder = new Order();
		newOrder.setQuantity(quantity);
		BuyStock item = buyRepo.findBySymbolAndUser(symbol, userData);
		if (item != null && quantity <= item.getQuantity()) {
			String jsonData = symbolData(symbol, quantity);
			String strPrice = getPricefromJSON(jsonData);
			String formattedprice =decimalformat.format(Double.valueOf(strPrice));
			double price = Double.parseDouble(formattedprice);
			decimalformat.format(price);
			logger.info("{} CURRENT PRICE IS {}",symbol, price);
			double totalprice = quantity * price;
			item.setQuantity(item.getQuantity() - quantity);
			SellStock buy = new SellStock();
			buy.setDate(currentDate);
			buy.setPrice(price);
			buy.setSymbol(symbol);
			buy.setUser(userData);
			buy.setType("SELL");
			buy.setQuantity(quantity);
			buy.setAvgPrice(totalprice);
			sellRepo.save(buy);
			logger.info("USER {} SOLD STOCKS OF {}  OF QUANTITY {} ", userData.getId(), symbol,  quantity);
			newOrder.setTime(currentDate.toString());
			newOrder.setType("SELL");
			newOrder.setAvgPrice(buy.getAvgPrice());
			newOrder.setSymbol(symbol);
			newOrder.setUser(userData);
			newOrder.setStatus("OPEN");
			orderRepo.save(newOrder);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("SOLD_STOCK");
			
		}
		logger.info("USER {} HAVE TROUBLE SELLING STOCK", userData.getId());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("USER_DON'T_HAVE_ENOUGH_QUANTITY_TO_SELL or NO_STOCK_BROUGHT_BY_THIS_USER");
	}

	public String buyStock(Authentication authentication, String symbol, double price, int quantity) {
		String email = authentication.getName();
		UserData userData = userRepo.findByEmail(email);
		Order newOrder = new Order();
		newOrder.setQuantity(quantity);
		logger.info("USER " + userData.getId() + " IS BUYING STOCKS OF " + symbol + " OF QUANTITY " + quantity);
		BuyStock item = buyRepo.findBySymbolAndUser(symbol, userData);
		if (item != null) {
			int oldQuantity = item.getQuantity();
			double oldAvgPrice = item.getAvgPrice();
			System.out.print(oldAvgPrice);
			double updatedprice = oldAvgPrice + (quantity * price);
			quantity = oldQuantity + quantity;
			item.setQuantity(quantity);
			item.setPrice(price);
			item.setAvgPrice(updatedprice);
			item.setDate(currentDate);
			item.setSymbol(symbol);
			item.setType("BUY");
			item.setUser(userData);
			newOrder.setAvgPrice(item.getAvgPrice());
			buyRepo.save(item);
		} else {
			BuyStock newitem = new BuyStock();
			double totalprice = (quantity * price);
			newitem.setQuantity(quantity);
			newitem.setAvgPrice(totalprice);
			Date date = new Date();
			newitem.setDate(date);
			newitem.setPrice(price);
			newitem.setSymbol(symbol);
			newitem.setType("BUY");
			newitem.setUser(userData);
			newOrder.setAvgPrice(newitem.getAvgPrice());
			buyRepo.save(newitem);

		}
		newOrder.setTime(currentDate.toString());
		newOrder.setType("BUY");
		newOrder.setSymbol(symbol);
		newOrder.setUser(userData);
		newOrder.setStatus("OPEN");
		orderRepo.save(newOrder);
		logger.info("USER {} BROUGHT STOCKS {}  OF QUANTITY {} ", userData.getId(),quantity);
		return "BUYING_STOCK";
	}

	public String orders(Authentication authentication) {
		String email = authentication.getName();
		UserData userData = userRepo.findByEmail(email);
		List<Order> allOrders = orderRepo.findAllByUser(userData);
		if (allOrders != null) {
			logger.info("GETTING ALL ORDERS FOR USER {}", userData.getFirstname());
			for (Order order : allOrders) {
				System.out.print(order);
			}
		}
		return "ORDERS";
	}

}