package com.fly.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.fly.dao.OrderDao;
import com.fly.domain.Order;
import com.fly.domain.OrderItems;
import com.fly.utils.DBUtils;

public class OrderDaoImpl implements  OrderDao {
	private QueryRunner queryRunner = new QueryRunner();
	private QueryRunner runner = new QueryRunner(DBUtils.getDataSource());
	
	public void saveOrder(Order order) {
		try {
			String sql = "insert into orders values(?, ?, ?, ?, ?, ?)";
			queryRunner.update(DBUtils.getCurrentConntection(), sql, order.getId(), order.getUid(), order.getTotalprice(), order.getAddress(), order.getStatus(), order.getCreatetime());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("插入订单数据失败");
		}
	}
	
	public void saveItems(OrderItems orderItems) {
		String sql = "insert into orderitems values(?, ?, ?)";
		try {
			queryRunner.update(DBUtils.getCurrentConntection(), sql, orderItems.getOid(), orderItems.getGid(), orderItems.getBuynum());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("插入订单项失败");
		}
	}

	public List<Order> findAllByUid(int id) throws SQLException {
		String sql  = "select * from orders where uid = ? order by createtime desc ";
		List<Order> list = runner.query(sql, new BeanListHandler<Order>(Order.class), id);
		return list;
	}

	public Order findOrderById(String id) throws SQLException {
		String sql = "select * from orders where id = ?";
		return runner.query(sql, new BeanHandler<Order>(Order.class), id);
	}

	public List<OrderItems> findOrderItemByOid(String id) throws SQLException {
		String sql = "select * from orderitems where oid = ?";
		List<OrderItems> itemsList = runner.query(sql, new BeanListHandler<OrderItems>(OrderItems.class), id);
		return itemsList;
	}

	public void deleteOrderById(String id) throws SQLException {
		String sql = "delete from orders where id = ?";
		runner.update(sql, id);
	}

	public void deleteOrderItemById(String id) throws SQLException {
		String sql = "delete from orderitems where oid = ?";
		runner.update(sql, id);
	}

	public List<Order> findOrderByStatus() {
		String sql = " select * from orders where status = 1 ";
		try {
			List<Order> list = runner.query(sql, new BeanListHandler<Order>(Order.class));
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("根据状态获取订单失败");
		}
	}

	public void update(Order order) {
		String sql = "update orders set status = 3 where id = ?";
		try {
			runner.update(sql, order.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("更新订单数信息失败");
		}
	}

}
