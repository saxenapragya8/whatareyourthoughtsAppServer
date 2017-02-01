package com.wayt.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wayt.responses.ConversationResponse;
import com.wayt.responses.FriendIdEmailNameResponse;

public class ConversationsDao {

	private Connection conn;
	private static ConversationsDao instance;
	
	String ADD_CONVERATION_QUERY = "{call add_new_conversation(?,?,?,?, ?, ?, ?)}";
	String ALL_USER_CONVERATIONS_QUERY = "select id, subject,user_id, source_link,slug, draft from conversations where id in (SELECT conversation_id FROM participations WHERE user_id=?)";
	String OTHER_USERS_IN_CONVERSATION = "select p.conversation_id , u.name from participations p, users u where p.conversation_id in (SELECT conversation_id FROM participations WHERE user_id=?) and p.user_id = u.id";
	
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

	public Boolean addNewConversation(int usrId, String subject, String articleLink, String slug, List<Integer> recipients, String content) throws SQLException, ClassNotFoundException {
		
		CallableStatement stmt = conn.prepareCall(ADD_CONVERATION_QUERY);
		try {
			stmt.setInt(1, usrId);
			stmt.setString(2, subject);
			stmt.setString(3, articleLink);
			stmt.setString(4, slug);
			stmt.setArray(5, conn.createArrayOf("varchar", recipients.toArray()));
			stmt.registerOutParameter(6, Types.BOOLEAN);
			stmt.setString(7, content);
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

	public Map<Integer, List<String>> getOtherUsersNamesInvolvedInThisUsersConvs(int usrId) throws SQLException, ClassNotFoundException {
		Map<Integer, List<String>> convIdUsers = new HashMap<Integer, List<String>>();
		CallableStatement stmt = conn.prepareCall(OTHER_USERS_IN_CONVERSATION);
		try {
			stmt.setInt(1, usrId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Integer id = rs.getInt("conversation_id");
				String name = rs.getString("name");
				if(convIdUsers.containsKey(id)){
					convIdUsers.get(id).add(name);
				} else {
					List<String> userNames = new ArrayList<String>();
					userNames.add(name);
					convIdUsers.put(id, userNames);
				}
			}
			return convIdUsers;

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
