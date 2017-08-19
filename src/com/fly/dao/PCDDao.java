package com.fly.dao;

import java.sql.SQLException;
import java.util.List;

import com.fly.domain.PCD;

public interface PCDDao {

	List<PCD> findByPid(int pid) throws SQLException;

}
