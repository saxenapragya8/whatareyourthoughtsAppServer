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
	String ADD_CHECK_USER_QUERY = "{call add_user(?,?,?,?, ?)}";

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
	
	public UserAuthResponse addUserIfNotPresent(String userName, String email, String userId) throws SQLException{
		CallableStatement stmt = conn.prepareCall(ADD_CHECK_USER_QUERY);
		try {
			stmt.setString(1, userName);
			stmt.setString(2, email);
			stmt.setString(3, email);
			stmt.registerOutParameter(4, Types.BOOLEAN);
			stmt.registerOutParameter(5, Types.INTEGER);
			stmt.execute();
			
			Integer waytUserId = stmt.getInt(5);
			boolean status = stmt.getBoolean(4);
			
			return new UserAuthResponse(waytUserId, status);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
}
