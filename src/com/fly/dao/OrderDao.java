package com.fly.dao;

import java.sql.SQLException;
import java.util.List;

import com.fly.domain.Order;
import com.fly.domain.OrderItems;

public interface OrderDao {

	void saveOrder(Order order);

	void saveItems(OrderItems orderItems);

	List<Order> findAllByUid(int id) throws SQLException;

	Order findOrderById(String id) throws SQLException;

	List<OrderItems> findOrderItemByOid(String id) throws SQLException;

	void deleteOrderById(String id) throws SQLException;

	void deleteOrderItemById(String id) throws SQLException;

	List<Order> findOrderByStatus();

	void update(Order order);


}
