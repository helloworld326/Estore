package com.fly.service.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fly.dao.CartDao;
import com.fly.dao.GoodDao;
import com.fly.dao.OrderDao;
import com.fly.dao.Impl.CartDaoImpl;
import com.fly.dao.Impl.GoodDaoImpl;
import com.fly.dao.Impl.OrderDaoImpl;
import com.fly.domain.Good;
import com.fly.domain.Order;
import com.fly.domain.OrderItems;
import com.fly.service.OrderService;
import com.fly.utils.DBUtils;

public class OrderServiceImpl implements OrderService {
	private OrderDao orderDao = new OrderDaoImpl();
	
	public void orderSubmit(int id, Order order) {
		CartDao cartDao = new CartDaoImpl();
		try {
			DBUtils.startTransaction();
			orderDao.saveOrder(order);
			for (OrderItems orderItems : order.getOiList()) {
				orderDao.saveItems(orderItems);
			}
			cartDao.deleteCartByUid(id);
			DBUtils.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				DBUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				DBUtils.release();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Order> findAllByUid(int id) {
		List<Order> orderList;
		try {
			orderList = orderDao.findAllByUid(id);
			return orderList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查找订单失败");
		}
	}

	public Order findOrderById(String id) {
		GoodDao goodDao = new GoodDaoImpl();
		Good good = new Good();
		List<OrderItems> list = new ArrayList<OrderItems>();
		try {
			Order order = orderDao.findOrderById(id);
			List<OrderItems> itemsList = orderDao.findOrderItemByOid(order.getId());
			for (OrderItems orderItems : itemsList) { //
				good.setId(orderItems.getGid());
				good = goodDao.findGoodById(good);
				orderItems.setGood(good);
				list.add(orderItems);
			}
			order.setOiList(itemsList);
			return order;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取指定Id订单失败");
		}
	}

	public void deleteOrderById(String id) {
		try {
			orderDao.deleteOrderItemById(id);
			orderDao.deleteOrderById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("删除订单失败");
		}
	}

	public void scan() {
		List<Order> orderList = orderDao.findOrderByStatus();
		for (Order order : orderList) {
			if (new Date().getTime() - order.getCreatetime().getTime() >= 1000 * 3600 * 2) {
				order.setStatus(3);
				orderDao.update(order);
			}
		}
	}
	
}
