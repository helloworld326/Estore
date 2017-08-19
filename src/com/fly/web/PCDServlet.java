package com.fly.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.fly.domain.PCD;
import com.fly.service.PCDService;
import com.fly.service.Impl.PCDServiceImpl;
import com.google.gson.Gson;

public class PCDServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public void listPCD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PCD pcd = new PCD();
		try {
			BeanUtils.populate(pcd, request.getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		PCDService pcdService = new PCDServiceImpl();
		List<PCD> pcdList = pcdService.findByPid(pcd);
		
		if(pcdList != null){
			Gson gson = new Gson();
			String pcdStr = gson.toJson(pcdList).toString();
			response.getWriter().write(pcdStr);
			return ;
		} else {
			response.sendRedirect(request.getContextPath() + "500.jsp");
			return ;
		}
		
	}

}
