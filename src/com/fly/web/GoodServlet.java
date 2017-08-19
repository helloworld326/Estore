package com.fly.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.fly.domain.Good;
import com.fly.service.GoodService;
import com.fly.service.Impl.GoodServiceImpl;

public class GoodServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		GoodService goodService = new GoodServiceImpl();
		List<Good> goodList = goodService.findAll();
		if(goodList != null){
			request.setAttribute("goodList", goodList);
			request.getRequestDispatcher("/goods.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/500.jsp");
		}
	}

	public void findGoodById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Good good = new Good();
		try {
			BeanUtils.populate(good, request.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		GoodService goodService = new GoodServiceImpl();
		good = goodService.findGoodById(good);
		if(good != null){
			request.setAttribute("good", good);
			request.getRequestDispatcher("/goods_detail.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/500.jsp");
		}
	}

}
