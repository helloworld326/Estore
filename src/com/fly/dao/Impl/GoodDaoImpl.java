package com.fly.dao.Impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.fly.dao.GoodDao;
import com.fly.domain.Good;
import com.fly.domain.Hot;
import com.fly.utils.DBUtils;

public class GoodDaoImpl implements GoodDao {
	private QueryRunner queryRunner = new QueryRunner(DBUtils.getDataSource());
	
	public List<Good> findAll() throws SQLException {
		String sql = "select * from goods";
		List<Good> list = queryRunner.query(sql, new BeanListHandler<Good>(Good.class));
		return list;
	}

	public Good findGoodById(Good good) throws SQLException {
		String sql = " select * from goods where id = ?";
		good = queryRunner.query(sql, new BeanHandler<Good>(Good.class), good.getId());
		return good;
	}

	public Good findGoodById(int gid) throws SQLException {
		String sql = "select * from goods where id = ?";
		return queryRunner.query(sql, new BeanHandler<Good>(Good.class), gid);
	}

	public int findGoodCount() {
		String sql = "select count(*) from goods";
		try {
			Long long1 = queryRunner.query(sql, new ScalarHandler<Long>());
			return long1.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询商品总数失败");
		}
	}

	public List<Good> findGoodByPage(int startIndex, Integer rows) {
		String sql = "select * from goods limit ?, ?";
		try {
			return queryRunner.query(sql, new BeanListHandler<Good>(Good.class), startIndex, rows);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("分页查询失败");
		}
	}

	public List<Hot> getHot() {

		String sql = "select oi.gid, g.name, sum(oi.buynum) hot from goods g, orderitems oi where g.id = oi.gid group by oi.gid order by hot desc";
		try {
			return queryRunner.query(sql, new BeanListHandler<Hot>(Hot.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("统计热门商品失败");
		}
	}

	public void add(Good good) {
		String sql = "insert into goods values(null, ?, ?, ?, ?, ?, ?, ?)";
		try {
			queryRunner.update(sql, good.getName(), good.getMarketprice(), good.getEstoreprice(), good.getCategory(), good.getNum(), good.getImgurl(), good.getDescription());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("文件插入失败");
		}
	}

}
