package com.fly.dao;

import java.sql.SQLException;
import java.util.List;

import com.fly.domain.Good;
import com.fly.domain.Hot;

public interface GoodDao {

	List<Good> findAll() throws SQLException;

	Good findGoodById(Good good) throws SQLException;

	Good findGoodById(int gid) throws SQLException;

	int findGoodCount();

	List<Good> findGoodByPage(int startIndex, Integer rows);

	List<Hot> getHot();

	void add(Good good);

}
