package com.fly.service;

import java.util.List;

import com.fly.domain.Order;

public interface OrderService {

	void orderSubmit(int id, Order order);

	List<Order> findAllByUid(int id);

	Order findOrderById(String id);

	void deleteOrderById(String id);

	void scan();
}
