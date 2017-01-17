package com.wayt.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.wayt.dao.CommentsDao;
import com.wayt.dao.ConversationsDao;
import com.wayt.dao.ParticipationsDao;
import com.wayt.responses.AllDisplayDataResponse;
import com.wayt.responses.CommentResponse;
import com.wayt.responses.ConversationResponse;
import com.wayt.responses.ParticipationResponse;

@Path("/displaydata")
public class AllDisplayDataService {

	@POST
	@Path("/getdata")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public AllDisplayDataResponse getAllData(@QueryParam(value = "usrId")int usrId) throws SQLException, ClassNotFoundException {
		List<ParticipationResponse> participations = ParticipationsDao.getInstance().getUserParticipations(usrId);
		List<ConversationResponse> conversations = ConversationsDao.getInstance().getUserConversations(usrId);
		List<CommentResponse> comments = CommentsDao.getInstance().getUserComments(usrId);
		Map<Integer, List<String>> convIdUsersMap = ConversationsDao.getInstance().getOtherUsersNamesInvolvedInThisUsersConvs(usrId);
		AllDisplayDataResponse allDisplayData = new AllDisplayDataResponse(convIdUsersMap, participations, conversations, comments);
		return allDisplayData;
	}
}
