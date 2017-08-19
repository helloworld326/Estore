package com.fly.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fly.domain.User;
import com.fly.service.UserService;
import com.fly.service.Impl.UserServiceImpl;

public class UserAutoLoginFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String cookie_username = "";
		String cookie_password = "";
		
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("username".equals(cookie.getName())){
				cookie_username = cookie.getValue();
			}
			
			if("password".equals(cookie.getName())){
				cookie_password = cookie.getValue();
			}
		}
		
		User user = new User();
		user.setUsername(cookie_username);
		user.setPassword(cookie_password);
		
		UserService userService = new UserServiceImpl();
		user = userService.findUserByUnamePsw(user);
		if(user != null){
			// 用户名密码正确;
			request.getSession().setAttribute("user", user);
		}
		chain.doFilter(request, response);
	}

	public void destroy() {

	}

}
