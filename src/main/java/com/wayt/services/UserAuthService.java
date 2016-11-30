package com.wayt.services;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.wayt.dao.UserDao;
import com.wayt.responses.UserAuthResponse;

@Path("/user")
public class UserAuthService {

	  @GET
	  @Path("/isuser")
	  @Produces(MediaType.APPLICATION_JSON)
	  public UserAuthResponse isValidUser(@QueryParam("username") String username, @QueryParam("passwd") String passwd) throws SQLException, ClassNotFoundException {
		return UserDao.getInstance().isUser(username, passwd);
	  }
}
