package com.fly.service;

import java.util.List;

import com.fly.domain.PCD;

public interface PCDService {

	List<PCD> findByPid(PCD pcd);

}
