package com.fly.dao;

import java.sql.SQLException;

import com.fly.domain.User;

public interface UserDao {

	User findUser(User user) throws SQLException;

	void createUser(User user) throws SQLException;
	
	User findUserByUnamePsw(String username, String password) throws SQLException;
	
}
