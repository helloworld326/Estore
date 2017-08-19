package com.fly.service;

import com.fly.domain.User;

public interface UserService {

	User findUser(User user);

	void createUser(User user);

	User findUserByUnamePsw(User user);
	
}
