package com.wayt.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import com.wayt.responses.FriendIdEmailNameResponse;

public class FriendshipDao {

	private Connection conn;
	private static FriendshipDao instance;
	
	String ADD_FRIENDSHIP_QUERY = "{call add_friendship(?,?,?,?)}";
	String FRIEND_EMAIL_AND_ID_QUERY = "select id, name, email from users where id in (select friend_id from friendships where user_id = ? and status = 'active' union select user_id from friendships where friend_id = ? and status = 'active')";

	private FriendshipDao() throws ClassNotFoundException, SQLException{
		DbConnection dbCon = DbConnection.getInstance();
		conn = dbCon.getConnection();
	}
	
	public static FriendshipDao getInstance() throws ClassNotFoundException, SQLException{
		if(instance == null){
			instance = new FriendshipDao();
		}
		return instance;
	}
	
	//function returns false if we need to notify the user using email
	public Boolean addFriendship(int usrId, String friendEmail) throws SQLException, ClassNotFoundException {
		
		CallableStatement stmt = conn.prepareCall(ADD_FRIENDSHIP_QUERY);
		try {
			stmt.setInt(1, usrId);
			stmt.setString(2, friendEmail);
			stmt.registerOutParameter(3, Types.INTEGER);
			stmt.registerOutParameter(4, Types.INTEGER);
			stmt.execute();
			
			int insertedNewFriendship = stmt.getInt(3);
			int isAlreadyAFriend = stmt.getInt(4);
			
			return newUserNeedsEmailNotification(insertedNewFriendship, isAlreadyAFriend);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
//		finally{
//			stmt.close();
//			dbCon.closeConnection();
//		}
	}

	private Boolean newUserNeedsEmailNotification(int insertedNewFriendship, int isAlreadyAFriend) {
		if(insertedNewFriendship == 0 && isAlreadyAFriend == 0){
			return true;
		}
		return false;
	}
	
	public List<FriendIdEmailNameResponse> getAllFriendsWithEmail(Integer usrId) throws SQLException, ClassNotFoundException{
//		try {
		List<FriendIdEmailNameResponse> friendList = new ArrayList<FriendIdEmailNameResponse>();
			PreparedStatement stmt = conn.prepareStatement(FRIEND_EMAIL_AND_ID_QUERY);
			stmt.setInt(1, usrId);
			stmt.setInt(2, usrId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				FriendIdEmailNameResponse friend = new FriendIdEmailNameResponse(rs.getInt("id"), rs.getString("email"), rs.getString("name"));
				friendList.add(friend);
			}
			return friendList;
//		} 
//		finally{
//			dbCon.closeConnection();
//		}
	}
}
