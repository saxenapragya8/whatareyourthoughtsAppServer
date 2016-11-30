package com.wayt.responses;

import java.io.Serializable;

@SuppressWarnings("serial")
public final class UserAuthResponse implements Serializable{

	private int userId;
	private boolean isUser;
	
	public UserAuthResponse(int userId, boolean isUser){
		this.userId = userId;
		this.isUser = isUser;
	}

	public int getUserId() {
		return userId;
	}

	public boolean isUser() {
		return isUser;
	}
}
