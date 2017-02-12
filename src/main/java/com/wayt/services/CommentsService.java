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
import com.wayt.dao.RegIdDao;
import com.wayt.notifications.NotificationSender;
import com.wayt.responses.CommentResponse;
import com.wayt.responses.ConversationResponse;
import com.wayt.responses.UpdateStatusResponse;

@Path("/comments")
public class CommentsService {

	NotificationSender sender = new NotificationSender();
	
	@POST
	@Path("/addcomment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UpdateStatusResponse addConversation(@QueryParam(value = "convId")int conversationId, @QueryParam(value = "userId") int userId, @QueryParam(value = "content") String content) throws SQLException, ClassNotFoundException {
		boolean addedStatus = CommentsDao.getInstance().addNewComment(conversationId, userId, content);
		List<String> regIds = RegIdDao.getInstance().getAllParticipantRegIds(conversationId, userId);
		for(String regId: regIds){
			if(!regId.isEmpty())
				sender.sendNewCommentNotification(userId, conversationId, regId);
		}
		return new UpdateStatusResponse(addedStatus);
	}
	
	@POST
	@Path("/getallusercomments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<CommentResponse> getAllUserComments(@QueryParam(value = "userId")int usrId) throws SQLException, ClassNotFoundException {
		return CommentsDao.getInstance().getUserComments(usrId);
	}
}
