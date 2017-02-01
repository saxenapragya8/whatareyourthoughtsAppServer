package com.wayt.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.wayt.responses.UserAuthResponse;

public class UserDao {

	private static UserDao instance;
	String IS_USER_QUERY = "select id, encrypted_password from users where email=?";
	String GET_ALL_RECIPIENT_IDS_QUERY = "select id from users where email = any(?)";
//	String CREATE_USER_QUERY = "{call create_user(?,?,?,?,?)}";
//	private String GET_EMAIL_FOR_USER = "select email from users where id=?";
//	private String SOME_USER_ADD_SPOUSE_PENDING_CHECK_QUERY = "select 1 from bt_couples where spouse_id = ? or user_id = ?";
//	private String GET_USR_ID_FOR_EMAIL = "select id from users where email=?";
//	private String GET_USR_NAME_FOR_ID = "select name from users where id = ?";
	private Connection conn;
	
	
	private UserDao() throws ClassNotFoundException, SQLException{
		DbConnection dbCon = DbConnection.getInstance();
		conn = dbCon.getConnection();
	}
	
	public static UserDao getInstance() throws ClassNotFoundException, SQLException{
		if(instance == null){
			instance = new UserDao();
		}
		return instance;
	}
	
	public UserAuthResponse isUser(String username, String passwd) throws SQLException {
		try {
			CallableStatement stmt = conn.prepareCall(IS_USER_QUERY);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			if (!rs.isBeforeFirst() ) {    
				 return new UserAuthResponse(-1, false); 
			} else {
				rs.next();
				Integer id = rs.getInt("id");
				boolean isValidUser = BCrypt.checkpw(passwd, rs.getString("encrypted_password"));
				return new UserAuthResponse(id, isValidUser);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 
//		finally{
//			dbCon.closeConnection();
//		}
		return new UserAuthResponse(-1, false); 
	}
	
	public List<Integer> getAllUserIdsForEmails(List<String> emails){
		Set<Integer> userIds = new HashSet<Integer>();
		try {
			CallableStatement stmt = conn.prepareCall(GET_ALL_RECIPIENT_IDS_QUERY);
			stmt.setArray(1, conn.createArrayOf("varchar", emails.toArray()));
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Integer id = rs.getInt("id");
				userIds.add(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		return new ArrayList<Integer>(userIds);
	}
}
