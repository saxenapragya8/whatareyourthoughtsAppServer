package com.wayt.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.wayt.responses.CommentResponse;
import com.wayt.responses.ConversationResponse;

public class CommentsDao {
	
	private Connection conn;
	private static CommentsDao instance;
	
	String GET_ALL_COMMENTS_BY_CONVERSATION_ID = "select id, participation_id, conversation_id, content from comments where participation_id in (SELECT id FROM participations WHERE user_id=?)";
	String ADD_NEW_COMMENT_QUERY = "insert into comments(participation_id, conversation_id, content, created_at, updated_at) values(?, ?, ?, now()::date, now()::date)";
	
	private CommentsDao() throws ClassNotFoundException, SQLException{
		DbConnection dbCon = DbConnection.getInstance();
		conn = dbCon.getConnection();
	}
	
	public static CommentsDao getInstance() throws ClassNotFoundException, SQLException{
		if(instance == null){
			instance = new CommentsDao();
		}
		return instance;
	}
	
	public List<CommentResponse> getUserComments(int usrId) throws SQLException, ClassNotFoundException {
		
		CallableStatement stmt = conn.prepareCall(GET_ALL_COMMENTS_BY_CONVERSATION_ID);
		try {
			stmt.setInt(1, usrId);
			ResultSet rs = stmt.executeQuery();
			List<CommentResponse> comments = new ArrayList<CommentResponse>();
			while(rs.next()){
				Integer id = rs.getInt("id");
				Integer participationId = rs.getInt("participation_id");
				Integer convId = rs.getInt("conversation_id");
				String content = rs.getString("content");
				comments.add(new CommentResponse(id, participationId, convId, content));
			}
			return comments;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Boolean addNewComment(Integer conversationId, Integer participationId, String content) throws SQLException{
		CallableStatement stmt = conn.prepareCall(ADD_NEW_COMMENT_QUERY);
		try {
			stmt.setInt(1, participationId);
			stmt.setInt(2, conversationId);
			stmt.setString(3, content);
			stmt.execute();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
}
