package com.wayt.responses;

import java.io.Serializable;

public class FriendIdEmailNameResponse  implements Serializable {

	private int friendId;
	private String email;
	private String name;
	
	public FriendIdEmailNameResponse(int friendId, String email, String name){
		this.friendId = friendId;
		this.email = email;
		this.name = name;
	}

	public int getFriendId() {
		return friendId;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}
}
