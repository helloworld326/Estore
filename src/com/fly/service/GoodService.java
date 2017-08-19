package com.fly.service;

import java.util.List;

import com.fly.domain.Good;
import com.fly.domain.Hot;
import com.fly.domain.PageBean;

public interface GoodService {

	List<Good> findAll();

	Good findGoodById(Good good);

	PageBean findGoodByPage(Integer rows, Integer page);

	List<Hot> getHot();

	void add(Good good);

}
