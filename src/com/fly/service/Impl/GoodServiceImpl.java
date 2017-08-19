package com.fly.service.Impl;

import java.sql.SQLException;
import java.util.List;

import com.fly.dao.GoodDao;
import com.fly.dao.Impl.GoodDaoImpl;
import com.fly.domain.Good;
import com.fly.domain.Hot;
import com.fly.domain.PageBean;
import com.fly.service.GoodService;

public class GoodServiceImpl implements GoodService {
	private GoodDao goodDao = new GoodDaoImpl();
	
	public List<Good> findAll() {
		List<Good> goodList = null;
		try {
			goodList = goodDao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return goodList;
	}

	public Good findGoodById(Good good) {
		try {
			return goodDao.findGoodById(good);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 当前页数
	public PageBean findGoodByPage(Integer rows, Integer page) {
		PageBean pageBean = new PageBean();
		// 通过查找总数计算返回总页数
		int count = goodDao.findGoodCount();
		int totalPage = count;
		pageBean.setTotalPage(totalPage);
		int startIndex = (page - 1) * rows;
		List<Good> goodList = goodDao.findGoodByPage(startIndex, rows);
		pageBean.setList(goodList);
		return pageBean;
	}

	public List<Hot> getHot() {
		List<Hot> hotList = goodDao.getHot();
		return hotList;
	}

	public void add(Good good) {
		goodDao.add(good);
	}
	
	
	
	
	
	
	
	

}
