package com.fly.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
public class DBUtils {
	private static final ComboPooledDataSource DATA_SOURCE = new ComboPooledDataSource();
	private static ThreadLocal<Connection> local = new ThreadLocal<Connection>();
	

	public static DataSource getDataSource() {
		return DATA_SOURCE;
	}

	public static Connection getConnection() throws SQLException {
		return DATA_SOURCE.getConnection();
	}
	
	public static Connection getCurrentConntection() throws SQLException {
		Connection connection = local.get();
		if(connection != null) {
			return connection;
		} else {
			// 如果池子中没有则添加;
			Connection connection2 = DBUtils.getConnection();
			local.set(connection2);
			return connection2;
		}
	}
	
	public static void startTransaction() throws SQLException{
		Connection conntection = getCurrentConntection();
		conntection.setAutoCommit(false);
	}
	
	public static void commit() throws SQLException{
		Connection conntection = getCurrentConntection();
		conntection.commit();
	}
	
	public static void rollback() throws SQLException{
		Connection conntection = getCurrentConntection();
		conntection.rollback();
	}
	
	public static void release() throws SQLException{
		Connection conntection = getCurrentConntection();
		if(conntection != null) {
			local.remove();
			conntection.close();
		}
	}
}
