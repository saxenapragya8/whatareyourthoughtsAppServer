package com.wayt.services;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.wayt.dao.FriendshipDao;
import com.wayt.responses.FriendIdEmailNameResponse;

@Path("/friendship")
public class FriendshipsService {

	@POST
	@Path("/addfriend")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addFriend(@QueryParam(value = "userId")int usrId, @QueryParam(value = "email") String emailId) throws SQLException, ClassNotFoundException {
		Boolean needsEmailNotify = FriendshipDao.getInstance().addFriendship(usrId, emailId);
		if(needsEmailNotify != null && needsEmailNotify){
			System.out.println("send the email notification");
		}
	 }
	
	@POST
	@Path("/getfriends")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<FriendIdEmailNameResponse> getAllFriends(@QueryParam(value = "userId")int usrId) throws SQLException, ClassNotFoundException {
		return FriendshipDao.getInstance().getAllFriendsWithEmail(usrId);
	 }
}
