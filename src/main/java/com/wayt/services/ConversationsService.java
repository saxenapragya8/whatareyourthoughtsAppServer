package com.wayt.services;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.wayt.dao.ConversationsDao;
import com.wayt.responses.ConversationResponse;

@Path("/conversations")
public class ConversationsService {
	
	@POST
	@Path("/addconversation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean addConversation(@QueryParam(value = "userId")int usrId, @QueryParam(value = "subject") String sub, @QueryParam(value = "link") String articleLink, @QueryParam(value = "slug") String slug, @QueryParam(value = "recipientIds") List<Integer> recipients) throws SQLException, ClassNotFoundException {
		return ConversationsDao.getInstance().addNewConversation(usrId, sub, articleLink, slug, recipients);
	}
	
	@POST
	@Path("/getalluserconversations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ConversationResponse> getAllUserConvs(@QueryParam(value = "userId")int usrId) throws SQLException, ClassNotFoundException {
		return ConversationsDao.getInstance().getUserConversations(usrId);
	}
}
