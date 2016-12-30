package com.wayt.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wayt.responses.ConversationResponse;
import com.wayt.responses.ParticipationResponse;

public class ParticipationsDao {
	private Connection conn;
	private static ParticipationsDao instance;
	
	String GET_ALL_USER_PARTICIPATIONS = "SELECT id, conversation_id, read,important,others_count,mute FROM participations WHERE user_id=?";
	
	private ParticipationsDao() throws ClassNotFoundException, SQLException{
		DbConnection dbCon = DbConnection.getInstance();
		conn = dbCon.getConnection();
	}
	
	public static ParticipationsDao getInstance() throws ClassNotFoundException, SQLException{
		if(instance == null){
			instance = new ParticipationsDao();
		}
		return instance;
	}
	

	public List<ParticipationResponse> getUserParticipations(int usrId) throws SQLException, ClassNotFoundException {
		
		CallableStatement stmt = conn.prepareCall(GET_ALL_USER_PARTICIPATIONS);
		try {
			stmt.setInt(1, usrId);
			ResultSet rs = stmt.executeQuery();
			List<ParticipationResponse> participations = new ArrayList<ParticipationResponse>();
			while(rs.next()){
				Integer id = rs.getInt("id");
				Integer convId = rs.getInt("conversation_id");
				boolean read = rs.getBoolean("read");
				boolean imp = rs.getBoolean("important");
				boolean mute = rs.getBoolean("mute");
				Integer otherCount = rs.getInt("others_count");
				participations.add(new ParticipationResponse(id, convId, read, imp, otherCount, mute));
			}
			return participations;

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
