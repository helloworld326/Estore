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
	 * tomcat���ȸ�������ͷ��������ͨ�����ʶ�Ӧ��xml����(����xml������Ҫ����)���ʶ�Ӧ��servletģ�飬Ȼ��ȥѰ��service�������Ӷ��ҵ�baseServlet,��Ϊ����д��httpServlet
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// ��ȡ method��������
			String methodName = request.getParameter("method");
			// ��ȡ�ֽ����ļ�����
			Class clazz = this.getClass(); // ?
			// ��ȡ����method����
			Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			super.service(request, response);
		}
	}

}
