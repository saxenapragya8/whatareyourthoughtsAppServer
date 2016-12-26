package com.wayt.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.wayt.responses.ConversationResponse;
import com.wayt.responses.FriendIdEmailNameResponse;

public class ConversationsDao {

	private Connection conn;
	private static ConversationsDao instance;
	
	String ADD_CONVERATION_QUERY = "{call add_new_conversation(?,?,?,?, ?, ?)}";
	String ALL_USER_CONVERATIONS_QUERY = "select id, subject, source_link, slug, draft from conversations where user_id=? ";
	
	private ConversationsDao() throws ClassNotFoundException, SQLException{
		DbConnection dbCon = DbConnection.getInstance();
		conn = dbCon.getConnection();
	}
	
	public static ConversationsDao getInstance() throws ClassNotFoundException, SQLException{
		if(instance == null){
			instance = new ConversationsDao();
		}
		return instance;
	}

	public Boolean addNewConversation(int usrId, String subject, String articleLink, String slug, List<Integer> recipients) throws SQLException, ClassNotFoundException {
		
		CallableStatement stmt = conn.prepareCall(ADD_CONVERATION_QUERY);
		try {
			stmt.setInt(1, usrId);
			stmt.setString(2, subject);
			stmt.setString(3, articleLink);
			stmt.setString(4, slug);
			stmt.setArray(5, conn.createArrayOf("varchar", (String[])recipients.toArray()));
			stmt.registerOutParameter(6, Types.BOOLEAN);
			stmt.execute();
			
			return stmt.getBoolean(6);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
//		finally{
//			stmt.close();
//			dbCon.closeConnection();
//		}
	}

	public List<ConversationResponse> getUserConversations(int usrId) throws SQLException, ClassNotFoundException {
		
		CallableStatement stmt = conn.prepareCall(ALL_USER_CONVERATIONS_QUERY);
		try {
			stmt.setInt(1, usrId);
			ResultSet rs = stmt.executeQuery();
			List<ConversationResponse> conversations = new ArrayList<ConversationResponse>();
			while(rs.next()){
				Integer id = rs.getInt("id");
				String subject = rs.getString("subject");
				String sourceLink = rs.getString("source_link");
				String slug = rs.getString("slug");
				Boolean draft = rs.getBoolean("draft");
				conversations.add(new ConversationResponse(id, subject, slug, draft, sourceLink));
			}
			return conversations;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
//		finally{
//			stmt.close();
//			dbCon.closeConnection();
//		}
	}
}
