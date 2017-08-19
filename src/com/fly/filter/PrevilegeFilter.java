package com.fly.filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fly.domain.User;

public class PrevilegeFilter implements Filter {
	private List<String> adminList = new ArrayList<String>();

	public void init(FilterConfig filterConfig) throws ServletException {

		String realPath = filterConfig.getServletContext().getRealPath("/WEB-INF/classes/admin.text");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(realPath)));
			String str = null;
			while ((str = reader.readLine()) != null) {
				adminList.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		// 获取请求url
		String uri = request.getRequestURI();
		uri = uri.substring(request.getContextPath().length());
		if (adminList.contains(uri)) { // 需要进行权限认证 
			User user = (User) request.getSession().getAttribute("user");
			if (user != null && user.getRole().equals("admin")) {
				chain.doFilter(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/login.jsp");
				return ;
			}
		} else { // 不需要管理
			chain.doFilter(request, response);
		}
	}

	public void destroy() {

	}

}
