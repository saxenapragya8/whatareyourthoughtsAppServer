package com.wayt.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class RegIdDao {

	private Connection conn;
	private static RegIdDao instance;

	String UPDATE_REQ_ID_QUERY = "update users set reg_id=?, updated_at=now()::timestamp where id=?";
	String SELECT_REQ_ID_QUERY = "select reg_id from users where id=?";
	
	private RegIdDao() throws ClassNotFoundException, SQLException{
		DbConnection dbCon = DbConnection.getInstance();
		conn = dbCon.getConnection();
	}
	
	public static RegIdDao getInstance() throws ClassNotFoundException, SQLException{
		if(instance == null){
			instance = new RegIdDao();
		}
		return instance;
	}
	
	public boolean updateRegId(Integer usrId, String regId) throws SQLException{
		try {
			
			PreparedStatement stmt = conn.prepareStatement(UPDATE_REQ_ID_QUERY);
			stmt.setString(1, regId);
			stmt.setInt(2, usrId);
			stmt.executeUpdate();
			return true;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
//		finally{
//			dbCon.closeConnection();
//		}
	}
	
	public String getRegId(Integer usrId) throws SQLException, ClassNotFoundException{
//		try {
			
			PreparedStatement stmt = conn.prepareStatement(SELECT_REQ_ID_QUERY);
			stmt.setInt(1, usrId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			String regId = rs.getString("reg_id");
			return regId;
//		} 
//		finally{
//			dbCon.closeConnection();
//		}
	}
}