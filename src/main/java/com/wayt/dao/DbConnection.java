package com.wayt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;


public final class DbConnection {

	private static BasicDataSource connectionPool = new BasicDataSource();
	private static final String driverClassName = "org.postgresql.Driver";
	private static DbConnection dbConn;
	private static final String url = System.getenv("DATABASE_URL");
	private static final String dbUsername = System.getenv("DATABASE_USERNAME");
	private static final String dbPassword = System.getenv("DATABASE_PASSWD");
	
	private DbConnection(){
		connectionPool.setUsername(dbUsername);
		connectionPool.setPassword(dbPassword);
		connectionPool.setDriverClassName(driverClassName);
		connectionPool.setUrl(url);
		connectionPool.setInitialSize(6);
		connectionPool.setMaxIdle(5);
		connectionPool.setMaxTotal(7);
		connectionPool.setValidationQuery("select 1");
	}
	
	public static DbConnection getInstance(){
		if(dbConn == null){
			dbConn = new DbConnection();
		}
		return dbConn;
	}

	public Connection getConnection() throws SQLException, ClassNotFoundException{
		return connectionPool.getConnection();
	}

}
