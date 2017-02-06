package com.wayt.services;

import java.sql.SQLException;
import java.util.List;

import javax.security.auth.Subject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.wayt.dao.ConversationsDao;
import com.wayt.dao.UserDao;
import com.wayt.notifications.NotificationSender;
import com.wayt.responses.ConversationResponse;
import com.wayt.responses.UpdateStatusResponse;

@Path("/conversations")
public class ConversationsService {
	
	NotificationSender sender = new NotificationSender();
	
	@POST
	@Path("/addconversation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UpdateStatusResponse addConversation(@QueryParam(value = "userId")int usrId, @QueryParam(value = "subject") String sub, @QueryParam(value = "link") String articleLink, @QueryParam(value = "recipientIds") List<String> recipients, @QueryParam(value = "content")String content) throws SQLException, ClassNotFoundException {
		List<Integer> recipientIds = UserDao.getInstance().getAllUserIdsForEmails(recipients);
		String slug = sub.replaceAll(" ", "-");
		boolean status = ConversationsDao.getInstance().addNewConversation(usrId, sub, articleLink, slug, recipientIds, content);
		for(Integer recipientId: recipientIds){
			sender.sendNotification(recipientId, sub);
		}
		return new UpdateStatusResponse(status);
	}
	
	@POST
	@Path("/getalluserconversations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ConversationResponse> getAllUserConvs(@QueryParam(value = "userId")int usrId) throws SQLException, ClassNotFoundException {
		return ConversationsDao.getInstance().getUserConversations(usrId);
	}
}
