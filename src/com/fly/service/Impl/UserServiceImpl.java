package com.fly.service.Impl;

import java.sql.SQLException;

import com.fly.dao.UserDao;
import com.fly.dao.Impl.UserDaoImpl;
import com.fly.domain.User;
import com.fly.service.UserService;
import com.fly.utils.MD5Utils;

public class UserServiceImpl implements UserService {

	public User findUser(User user) {
		UserDao ud = new UserDaoImpl();
		try {
			user = ud.findUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public void createUser(User user) {
		UserDao ud = new UserDaoImpl();
		String password = MD5Utils.getPassword(user.getPassword());
		user.setPassword(password);
		try {
			ud.createUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public User findUserByUnamePsw(User user) {
		UserDao ud = new UserDaoImpl();
		String password = MD5Utils.getPassword(user.getPassword());
		user.setPassword(password);
		try {
			user = ud.findUserByUnamePsw(user.getUsername(), user.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

}
