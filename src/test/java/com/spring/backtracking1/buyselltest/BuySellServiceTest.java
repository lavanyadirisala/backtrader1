/*
 * package com.spring.backtracking1.buyselltest;
 * 
 * import static org.junit.Assert.assertNull; import static
 * org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.mockito.Matchers.any; import static org.mockito.Matchers.anyString;
 * import static org.mockito.Mockito.when;
 * 
 * import java.io.IOException; import java.util.ArrayList; import
 * java.util.Arrays; import java.util.List;
 * 
 * import org.junit.Test; import org.mockito.InjectMocks; import
 * org.mockito.Mock; import org.mockito.Mockito; import
 * org.springframework.security.core.Authentication;
 * 
 * import com.spring.backtracking1.entity.BuyStock; import
 * com.spring.backtracking1.entity.Order; import
 * com.spring.backtracking1.entity.UserData; import
 * com.spring.backtracking1.repository.BuyStockRepository; import
 * com.spring.backtracking1.repository.OrdersRepository; import
 * com.spring.backtracking1.repository.UserRepository; import
 * com.spring.backtracking1.service.BuySellStockService;
 * 
 * public class BuySellServiceTest {
 * 
 * @Mock UserRepository userRepo;
 * 
 * @Mock BuyStockRepository buyRepo;
 * 
 * @Mock OrdersRepository orderRepo;
 * 
 * @InjectMocks BuySellStockService buySellStockService;
 * 
 * @Test public void testGetPricefromJSON_MissingGlobalQuoteKey() { String
 * jsonData = "{\"some_other_key\": \"some_value\"}"; String price =
 * buySellStockService.getPricefromJSON(jsonData); assertNull(price); }
 * 
 * @Test public void testGetPricefromJSON_MissingPriceKey() { String jsonData =
 * "{\"Global Quote\": {\"some_other_key\": \"some_value\"}}"; String price =
 * buySellStockService.getPricefromJSON(jsonData); assertNull(price); }
 * 
 * @Test public void testBuyStock_ExistingStockEntry() { UserData userData = new
 * UserData(); userData.setId(1); userData.setEmail("user@example.com");
 * 
 * BuyStock existingBuyStock = new BuyStock();
 * existingBuyStock.setUser(userData); existingBuyStock.setSymbol("AAPL");
 * existingBuyStock.setQuantity(5); existingBuyStock.setAvgPrice(200.0);
 * 
 * when(userRepo.findByEmail(anyString())).thenReturn(userData);
 * when(buyRepo.findBySymbolAndUser(anyString(),
 * any(UserData.class))).thenReturn(existingBuyStock);
 * 
 * // Call the method String result =
 * buySellStockService.buyStock(Mockito.mock(Authentication.class), "AAPL",
 * 250.0, 5);
 * 
 * // Assert the result assertEquals("BUYING_STOCK", result); assertEquals(10,
 * existingBuyStock.getQuantity()); assertEquals(2250.0,
 * existingBuyStock.getAvgPrice(), 0.001); // Updated average price }
 * 
 * @Test public void testGetUserOrders_EmptyOrderList() { UserData userData =
 * new UserData(); userData.setId(1); userData.setEmail("user@example.com");
 * 
 * List<Order> orders = new ArrayList<>();
 * 
 * when(userRepo.findByEmail(anyString())).thenReturn(userData);
 * when(orderRepo.findAllByUser(any(UserData.class))).thenReturn(orders);
 * 
 * // Call the method String result =
 * buySellStockService.orders(Mockito.mock(Authentication.class));
 * 
 * // Assert the result or perform further verifications assertEquals("ORDERS",
 * result); Mockito.verify(orderRepo, Mockito.times(1)).findAllByUser(userData);
 * }
 * 
 * @Test public void testSuccessfulStockSale() throws IOException,
 * InterruptedException { // Mock the required repositories and dependencies
 * UserData userData = new UserData(); userData.setId(1);
 * userData.setEmail("user@example.com");
 * 
 * BuyStock buyStock = new BuyStock(); buyStock.setUser(userData);
 * buyStock.setSymbol("AAPL"); buyStock.setQuantity(10);
 * buyStock.setAvgPrice(200.0);
 * 
 * Mockito.when(userRepo.findByEmail(Mockito.anyString())).thenReturn(userData);
 * Mockito.when(buyRepo.findBySymbolAndUser(Mockito.anyString(),
 * Mockito.any(UserData.class))).thenReturn(buyStock);
 * 
 * // Call the method String result =
 * buySellStockService.sellStock(Mockito.mock(Authentication.class), "AAPL", 5);
 * 
 * // Assert the result assertEquals("SOLD_STOCK", result); assertEquals(5,
 * buyStock.getQuantity()); }
 * 
 * @Test public void testInsufficientQuantityForSale() throws IOException,
 * InterruptedException { // Mock the required repositories and dependencies
 * UserData userData = new UserData(); userData.setId(1);
 * userData.setEmail("user@example.com");
 * 
 * BuyStock buyStock = new BuyStock(); buyStock.setUser(userData);
 * buyStock.setSymbol("AAPL"); buyStock.setQuantity(3);
 * buyStock.setAvgPrice(200.0);
 * 
 * Mockito.when(userRepo.findByEmail(Mockito.anyString())).thenReturn(userData);
 * Mockito.when(buyRepo.findBySymbolAndUser(Mockito.anyString(),
 * Mockito.any(UserData.class))).thenReturn(buyStock);
 * 
 * // Call the method String result =
 * buySellStockService.sellStock(Mockito.mock(Authentication.class), "AAPL", 5);
 * 
 * // Assert the result
 * assertEquals("USER_DON'T_HAVE_ENOUGH_QUANTITY_TO_SELL or NO_STOCK_BROUGHT_BY_THIS_USER"
 * , result); assertEquals(3, buyStock.getQuantity()); // Quantity remains
 * unchanged }
 * 
 * @Test public void testSuccessfulStockPurchase() { // Mock the required
 * repositories and dependencies UserData userData = new UserData();
 * userData.setId(1); userData.setEmail("user@example.com");
 * 
 * Mockito.when(userRepo.findByEmail(Mockito.anyString())).thenReturn(userData);
 * Mockito.when(buyRepo.findBySymbolAndUser(Mockito.anyString(),
 * Mockito.any(UserData.class))).thenReturn(null);
 * 
 * // Call the method String result =
 * buySellStockService.buyStock(Mockito.mock(Authentication.class), "AAPL",
 * 250.0, 5);
 * 
 * // Assert the result assertEquals("BUYING_STOCK", result);
 * Mockito.verify(buyRepo, Mockito.times(1)).save(Mockito.any(BuyStock.class));
 * }
 * 
 * @Test public void testStockPurchaseExistingStocks() { // Mock the required
 * repositories and dependencies UserData userData = new UserData();
 * userData.setId(1); userData.setEmail("user@example.com");
 * 
 * BuyStock existingBuyStock = new BuyStock();
 * existingBuyStock.setUser(userData); existingBuyStock.setSymbol("AAPL");
 * existingBuyStock.setQuantity(5); existingBuyStock.setAvgPrice(200.0);
 * 
 * Mockito.when(userRepo.findByEmail(Mockito.anyString())).thenReturn(userData);
 * Mockito.when(buyRepo.findBySymbolAndUser(Mockito.anyString(),
 * Mockito.any(UserData.class))).thenReturn(existingBuyStock);
 * 
 * // Call the method String result =
 * buySellStockService.buyStock(Mockito.mock(Authentication.class), "AAPL",
 * 250.0, 5);
 * 
 * // Assert the result assertEquals("BUYING_STOCK", result); assertEquals(10,
 * existingBuyStock.getQuantity()); }
 * 
 * @Test public void testGetUserOrders() { // Mock the required repositories and
 * dependencies UserData userData = new UserData(); userData.setId(1);
 * userData.setEmail("user@example.com");
 * 
 * Order order1 = new Order(); order1.setUser(userData);
 * order1.setSymbol("AAPL"); order1.setQuantity(5);
 * 
 * Order order2 = new Order(); order2.setUser(userData);
 * order2.setSymbol("GOOGL"); order2.setQuantity(10);
 * 
 * List<Order> orders = Arrays.asList(order1, order2);
 * Mockito.when(userRepo.findByEmail(Mockito.anyString())).thenReturn(userData);
 * Mockito.when(orderRepo.findAllByUser(Mockito.any(UserData.class))).thenReturn
 * (orders);
 * 
 * // Call the method String result =
 * buySellStockService.orders(Mockito.mock(Authentication.class));
 * 
 * // Assert the result or perform further verifications assertEquals("ORDERS",
 * result); Mockito.verify(orderRepo, Mockito.times(1)).findAllByUser(userData);
 * }
 * 
 * }
 */