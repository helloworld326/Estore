package com.fly.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fly.domain.Cart;
import com.fly.domain.Order;
import com.fly.domain.OrderItems;
import com.fly.domain.User;
import com.fly.service.OrderService;
import com.fly.service.Impl.OrderServiceImpl;
import com.fly.utils.UUIDUtils;

@SuppressWarnings("all")
public class OrderServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	private OrderService orderService = new OrderServiceImpl();

	public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null) {
			String msg = "您还未登陆,等先登陆！";
			session.removeAttribute("msg");
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return ;
		}
		
		List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
		Order order = new Order();
		List<OrderItems> itemsList = new ArrayList<OrderItems>(); 
		
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		String address = request.getParameter("address");
		String receiver = request.getParameter("receiver");
		String zipcode = request.getParameter("zipcode");
		String telephone = request.getParameter("telephone");
		
		String receiverInfo = province + "省/市," + city + "市/县," + district + "县/区," +  "邮编:" + zipcode + ":" + "收件人:" + receiver + "电话:" + telephone;
		order.setAddress(receiverInfo);
		order.setCreatetime(new Date());
		String oid = UUIDUtils.getUUID();
		order.setId(oid);
		double totalPrice = 0;
		order.setStatus(1);
		for (Cart cart : cartList) {
			OrderItems orderItems = new OrderItems(); 
			orderItems.setGid(cart.getGid()); // ��Ʒ���
			orderItems.setOid(oid);
			orderItems.setBuynum(cart.getBuynum()); // ��������
			orderItems.setGood(cart.getGood()); // �����Ʒ
			totalPrice += cart.getBuynum() * cart.getGood().getEstoreprice(); // �ܼ۸�
			itemsList.add(orderItems);
		}
		order.setOiList(itemsList);
		order.setTotalprice(totalPrice);
		order.setUid(user.getId());
		orderService.orderSubmit(user.getId(), order);
		session.setAttribute("order", order);
		request.getRequestDispatcher("/orders_detail.jsp").forward(request, response);
	}
	
	public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			String msg = "您还未登陆,等先登陆！";
			session.removeAttribute("msg");
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return ;
		}
		
		Order order = new Order();
		order.setUid(user.getId());
		List<Order> orderList = orderService.findAllByUid(user.getId());
		session.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/orders.jsp").forward(request, response);
	}
	
	public void findOrderById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null) {
			String msg = "您还未登陆,等先登陆！";
			session.removeAttribute("msg");
			session.setAttribute("msg", msg);
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return ;
		}
		
		String id = request.getParameter("id");
		Order order = new Order();
		order.setId(id);
		order = orderService.findOrderById(id);
		if(order != null) {
			session.setAttribute("order", order);
			request.getRequestDispatcher("/orders_detail.jsp").forward(request, response);
		}
	}

	public void deleteOrderById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		orderService.deleteOrderById(id);
		findAll(request, response);
		return ;
	}
}
