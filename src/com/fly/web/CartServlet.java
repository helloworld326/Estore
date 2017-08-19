package com.fly.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.fly.domain.Cart;
import com.fly.domain.User;
import com.fly.service.CartService;
import com.fly.service.Impl.CartServiceImpl;

public class CartServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null) {
			String refererCart = request.getHeader("referer");
			String msg = "���ȵ�½!";
			session.setAttribute("msg", msg);
			session.setAttribute("refererCart", refererCart); // ��½��ֱ����ת�����һ�η�����Ʒ��ҳ��
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return ;
		}
		
		// �û��Ѿ���½
		String gid = request.getParameter("gid");
		Cart cart = new Cart();
		cart.setUid(user.getId());
		cart.setGid(Integer.parseInt(gid));
		CartService cartService = new CartServiceImpl();
		int condition = cartService.addToCart(cart);
		if(condition == -1){
			// ���ʧ��
			response.sendRedirect(request.getContextPath() + "/500.jsp");
			return ;
		}
		
		response.sendRedirect(request.getContextPath() + "/buyorcart.jsp");
	}

	public void listCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null){
			// ��ת����½ҳ��
			String msg = "���ȵ�½!";
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return ;
		}
		
		// �û��Ѿ���¼
		CartService cartService = new CartServiceImpl();
		List<Cart> cartList = cartService.findAllByUid(user);
		request.getSession().setAttribute("cartList", cartList);
		
		// ��ת�������ύҳ��
		String flag = request.getParameter("flag");
		if(StringUtils.isNotBlank(flag)){
			response.sendRedirect(request.getContextPath() + "/orders_submit.jsp");
			return ;
		}
		
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}
	
	public void deleteCartGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String gid = request.getParameter("gid");
		if(StringUtils.isNotBlank(gid)){
			CartService cartService = new CartServiceImpl();
			cartService.deleteCartGoodByGid(Integer.parseInt(gid));
			response.sendRedirect(request.getContextPath() + "/cart?method=listCart");
			return ;
		} else {
			response.sendRedirect(request.getContextPath() + "/500.jsp");
		}
	}
	
	public void updateCartNum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = new Cart();
		try {
			BeanUtils.populate(cart, request.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		CartService cartService = new CartServiceImpl();
		User user = (User) request.getSession().getAttribute("user");
		cart.setUid(user.getId());
		cartService.updateCartNum(cart);
		
		listCart(request, response);
	}

	// �ύ��������
//	public void cartToSubmit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		listCart(request, response);
//		response.sendRedirect(request.getContextPath() + "/orders_submit.jsp");
//	}
	
}
