package com.fly.service.Impl;

import java.sql.SQLException;
import java.util.List;

import com.fly.dao.PCDDao;
import com.fly.dao.Impl.PCDDaoImpl;
import com.fly.domain.PCD;
import com.fly.service.PCDService;

public class PCDServiceImpl implements PCDService {
	private PCDDao pcdDao = new PCDDaoImpl();

	public List<PCD> findByPid(PCD pcd) {
		List<PCD> pcdList = null;
		try {
			pcdList = pcdDao.findByPid(pcd.getPid());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pcdList;
	}

}
