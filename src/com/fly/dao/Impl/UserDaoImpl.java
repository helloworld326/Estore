package com.fly.dao.Impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.fly.dao.UserDao;
import com.fly.domain.User;
import com.fly.utils.DBUtils;

public class UserDaoImpl implements UserDao {
	QueryRunner qr = new QueryRunner(DBUtils.getDataSource());

	public User findUser(User user) throws SQLException {
		String sql = "select * from user where username = ? ";
		user = qr.query(sql, new BeanHandler<User>(User.class), user.getUsername());
		return user;
	}

	public void createUser(User user) throws SQLException {
		String sql = "insert into user(username, nickname, password, role) value(?, ? , ?, ?)";
		qr.update(sql, user.getUsername(), user.getNickname(), user.getPassword(), "user");
	}
	
	public User findUserByUnamePsw(String username, String password) throws SQLException {
		String sql = "select * from user where username = ? and password = ?";
		User user = qr.query(sql, new BeanHandler<User>(User.class), username, password);
		return user;
	}

}
