package com.fly.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.fly.dao.PCDDao;
import com.fly.domain.PCD;
import com.fly.utils.DBUtils;

public class PCDDaoImpl implements PCDDao {
	private QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
	
	public List<PCD> findByPid(int pid) throws SQLException {
		String sql = "select * from province_city_district where pid = ?";
		List<PCD> list = queryRunner.query(sql, new BeanListHandler<PCD>(PCD.class), pid);
		return list;
	}

}
