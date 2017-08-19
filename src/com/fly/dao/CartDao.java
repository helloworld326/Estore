package com.fly.dao;

import java.sql.SQLException;
import java.util.List;

import com.fly.domain.Cart;
import com.fly.domain.User;

public interface CartDao {

	Cart findByUidAndGid(int uid, int gid) throws SQLException;

	void addCart(Cart cart) throws SQLException;

	void update(int uid, int gid, int buynum) throws SQLException;

	List<Cart> findAllByUid(User user) throws SQLException;

	void deleteCartGoodByGid(int gid) throws SQLException;

	void deleteCartByUid(int id) throws SQLException;

}
