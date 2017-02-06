package com.wayt.notifications;

import java.io.Serializable;

public class SendData implements Serializable{

	INotificationData data;
	String to;
	Notification notification;
	
	public SendData(INotificationData data, String to,Notification notification){
		this.data = data;
		this.to = to;
		this.notification = notification;
	}

	public INotificationData getData() {
		return data;
	}

	public void setData(INotificationData data) {
		this.data = data;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	
}
