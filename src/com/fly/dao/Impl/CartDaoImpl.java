package com.fly.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.fly.dao.CartDao;
import com.fly.domain.Cart;
import com.fly.domain.User;
import com.fly.utils.DBUtils;

public class CartDaoImpl implements CartDao {
	private QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
	
	public Cart findByUidAndGid(int uid, int gid) throws SQLException {
		String sql = "select * from cart where uid = ? and gid = ?";
		return queryRunner.query(sql, new BeanHandler<Cart>(Cart.class), uid, gid);
	}
	
	public void addCart(Cart cart) throws SQLException {
		String sql = "insert into cart values(?, ?, ?)";
		queryRunner.update(sql, cart.getUid(), cart.getGid(), cart.getBuynum());
	}
	
	public void update(int uid, int gid, int buynum) throws SQLException {
		String sql = "update cart set buynum = ? where uid = ? and gid = ?"; 
		queryRunner.update(sql, buynum, uid, gid);
	}

	public List<Cart> findAllByUid(User user) throws SQLException {
		String sql = "select * from cart where uid = ?";
		return queryRunner.query(sql, new BeanListHandler<Cart>(Cart.class), user.getId());
	}

	public void deleteCartGoodByGid(int gid) throws SQLException {
		String sql = "delete from cart where gid = ?";
		queryRunner.update(sql, gid);
	}

	public void deleteCartByUid(int id) throws SQLException {
		String sql = "delete from cart where uid = ?";
		queryRunner.update(sql, id);
		
	}

}
