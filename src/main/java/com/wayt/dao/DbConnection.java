package com.wayt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;


public final class DbConnection {

	private static BasicDataSource connectionPool = new BasicDataSource();
	private static final String driverClassName = "org.postgresql.Driver";
//    private static final String url = "jdbc:postgresql://ec2-107-20-234-127.compute-1.amazonaws.com:5432/deprj3m0b5sovp?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
//	private static final String dbUsername = "jjtlhkinzgzagc";
//	private static final String dbPassword = "6t2FFiFAicJF2OctagzZYzqIlV";
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
