package com.wayt.services;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.wayt.dao.RegIdDao;

@Path("/regid")
public class RegIdService {
	  
	  @POST
	  @Path("/getregid")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	  public String getRegId(@QueryParam(value = "userId")int usrId) throws SQLException, ClassNotFoundException {
		return RegIdDao.getInstance().getRegId(usrId);
	  }
	  
	  @POST
	  @Path("/updateregid")
	  @Consumes(MediaType.APPLICATION_JSON)
	  @Produces(MediaType.APPLICATION_JSON)
	  public boolean updateRegId(@QueryParam(value = "userId") int usrId, @QueryParam(value = "registrationId") String regId) throws SQLException, ClassNotFoundException {
		return RegIdDao.getInstance().updateRegId(usrId, regId);
	  }
}
