package com.spring.backtracking1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.backtracking1.entity.BuyStock;
import com.spring.backtracking1.entity.Order;
import com.spring.backtracking1.entity.UserData;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
	List<Order> findAllByUser(UserData user);

}
