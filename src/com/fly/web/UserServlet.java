package com.fly.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.fly.domain.User;
import com.fly.service.UserService;
import com.fly.service.Impl.UserServiceImpl;
import com.google.gson.Gson;

@SuppressWarnings("all")
public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	// ע�Ṧ��
	public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isPass = checkRegisterInfo(request, response);
		if(!isPass)	return ;
		
		// ͨ�����ע����Ϣ��֤
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserService us = new UserServiceImpl();
		us.createUser(user);
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	// ajaxУ����֤��
	public void imgCodeCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String checkImg = request.getParameter("captcha"); // �����ݹ�������֤��
		HttpSession session = request.getSession();
		boolean flag = false;
		String word = (String) session.getAttribute("code"); // ��̨���ɵ���֤��
		flag = word.equalsIgnoreCase(checkImg) ? true : false; // true��ʾ��ȷ��false��ʾ����
		Gson gson = new Gson();
		String checkInfo = gson.toJson(flag).toString();
		session.setAttribute("flag", flag);
		response.getWriter().write(checkInfo);
	}
	
	// ajax��֤�û����Ƿ��Ѿ�����
	public void checkUsernameIsExist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserService us = new UserServiceImpl();
		user = us.findUser(user);
		boolean flag = false;
		if(user == null) {
			flag = true;
		}
		Gson gson = new Gson();
		String checkInfo = gson.toJson(flag).toString();
		session.setAttribute("isUserExist", flag);
		response.getWriter().write(checkInfo);
	}
	
	// ��¼����
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		String remember = request.getParameter("remember");
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String password = user.getPassword();
		UserService us = new UserServiceImpl();
		user = us.findUserByUnamePsw(user);
		if(user == null){
			String loginInfo = "用户名或密码错误";
			session.setAttribute("loginInfo", loginInfo);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			Cookie cookie = null;
			if("on".equals(remember)){ // �û���ѡ��ס�û���
				cookie = new Cookie("username", URLEncoder.encode(user.getUsername(), "UTF-8"));
				cookie.setMaxAge(3600 * 24 * 7);
				cookie.setPath("/");
			} else {
				cookie = new Cookie("username", "");
				cookie.setMaxAge(0);
				cookie.setPath("/");
			}
			response.addCookie(cookie);
			session.setAttribute("user", user);
			String refererCart = (String) session.getAttribute("refererCart");
			if(refererCart != null){
				session.removeAttribute("refererCart");
				response.sendRedirect(refererCart);
				return ;
			}
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
	}
	
	// ע��ҳ����Ϣ���У��
	public boolean checkRegisterInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Boolean isCaptcha = (Boolean) session.getAttribute("flag");
		String msg = "";
		session.removeAttribute("msg");
		
		if(!isCaptcha) {
			msg = "验证码不能为空";
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/register.jsp");
			return false;
		}
		
		String agree = request.getParameter("agreement");
		if(agree == null){
			msg = "请先阅读协议";
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/register.jsp");
			return false;
		}
		
		if(!isCaptcha) {
			msg = "验证码错误";
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/register.jsp");
			return false;
		}
		
		Boolean isUserExist = (Boolean) session.getAttribute("isUserExist");
		if(!isUserExist){
			msg = "用户名不存在";
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/register.jsp");
			return false;
		}
		
		String username = request.getParameter("username");
		if(StringUtils.isBlank(username)) {
			msg = "用户名不能为空";
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/register.jsp");
			return false;
		}
		
		String nickname = request.getParameter("nickname");
		if(StringUtils.isBlank(nickname)){
			msg = "昵称不能为空";
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/register.jsp");
			return false;
		}
		
		String password = request.getParameter("password");
		if(StringUtils.isBlank(password)) {
			msg = "密码不能为空";
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/register.jsp");
			return false;
		}
		
		String confirmPwd = request.getParameter("confirm_password");
		if(StringUtils.isBlank(confirmPwd)) {
			msg = "确认密码不能为空";
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/register.jsp");
			return false;
		}
		
		if(!password.equals(confirmPwd)){
			msg = "两次密码不一致";
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/register.jsp");
			return false;
		}
		return true;
	}

	// �û�ע��
	/*public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String referer = request.getHeader("referer");
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		response.sendRedirect(referer);
	}*/
	
	// ���cookie
	/*
	public void addCookie(String username, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie cookie_username = new Cookie("username", username);
		Cookie cookie_password = new Cookie("password", password);
		
		cookie_username.setPath("/");
		cookie_password.setPath("/");
		
		cookie_username.setMaxAge(60 * 60 * 24);
		cookie_password.setMaxAge(60 * 60 * 24);
		
		response.addCookie(cookie_username);
		response.addCookie(cookie_password);
	}
	*/
	
	// ɾ��cookie
	/*
	public void clearCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie cookie_username = new Cookie("username", "");
		Cookie cookie_password = new Cookie("password", "");
		
		cookie_username.setPath("/");
		cookie_password.setPath("/");
		
		cookie_username.setMaxAge(0);
		cookie_password.setMaxAge(0);
		
		response.addCookie(cookie_username);
		response.addCookie(cookie_password);
	}
	*/
}
