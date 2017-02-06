package com.wayt.notifications;
import com.wayt.notifications.INotificationData;;

public class NewConvNotificationData implements INotificationData{

	private String message;
	private String newConv;
	
	public NewConvNotificationData(String msg, String newConv){
		this.message = msg;
		this.newConv = newConv;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNewConv() {
		return newConv;
	}

	public void setNewConv(String newConv) {
		this.newConv = newConv;
	}
}
