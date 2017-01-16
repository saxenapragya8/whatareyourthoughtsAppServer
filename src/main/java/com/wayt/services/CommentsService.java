package com.wayt.services;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.wayt.dao.CommentsDao;
import com.wayt.dao.ConversationsDao;
import com.wayt.responses.CommentResponse;
import com.wayt.responses.ConversationResponse;

@Path("/comments")
public class CommentsService {

	@POST
	@Path("/addcomment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addConversation(@QueryParam(value = "convId")int conversationId, @QueryParam(value = "participationId") int participationId, @QueryParam(value = "content") String content) throws SQLException, ClassNotFoundException {
		return CommentsDao.getInstance().addNewComment(conversationId, participationId, content);
	}
	
	@POST
	@Path("/getallusercomments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<CommentResponse> getAllUserComments(@QueryParam(value = "userId")int usrId) throws SQLException, ClassNotFoundException {
		return CommentsDao.getInstance().getUserComments(usrId);
	}
}
