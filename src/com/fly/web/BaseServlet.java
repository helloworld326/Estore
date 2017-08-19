package com.fly.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("all")
public class BaseServlet extends HttpServlet {

	/**
	 * tomcat会先根据请求头方法参数通过访问对应的xml配置(所以xml并不需要更改)访问对应的servlet模块，然后去寻找service方法，从而找到baseServlet,因为他重写了httpServlet
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 获取 method方法名称
			String methodName = request.getParameter("method");
			// 获取字节码文件对象
			Class clazz = this.getClass(); // ?
			// 获取对象method方法
			Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			super.service(request, response);
		}
	}

}
