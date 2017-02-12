package com.wayt.notifications;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.wayt.dao.ConversationsDao;
import com.wayt.dao.RegIdDao;

public class NotificationSender {

	private String FIREBASE_URL = "https://fcm.googleapis.com/fcm/send";
	private RestTemplate template;
	
	public NotificationSender(){
		template = new RestTemplate();
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
	    converters.add(new StringHttpMessageConverter());
	    converters.add(new Jaxb2RootElementHttpMessageConverter());
	    converters.add(new MappingJackson2HttpMessageConverter());
	    template.setMessageConverters(converters);
	}
	
	public void sendNotification(Integer userId, String convAdded) throws ClassNotFoundException, SQLException{
		String regId = RegIdDao.getInstance().getRegId(userId);
		NewConvNotificationData notificationData = new NewConvNotificationData("New conversation added", convAdded);
		Notification notification = new Notification("New conversation added", convAdded);
		SendData data = new SendData(notificationData, regId, notification);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.set("Authorization","key=" + System.getenv("AUTH_KEY"));
		HttpEntity<SendData> requestEntity = new HttpEntity<SendData>(data, requestHeaders);
		try{
			ResponseEntity<String> response = template.exchange(FIREBASE_URL, HttpMethod.POST, requestEntity, String.class);
			System.out.println(response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendNewCommentNotification(Integer userId, Integer convId, String regId) throws ClassNotFoundException, SQLException{
		String convTitle = ConversationsDao.getInstance().getConvTitle(convId);
		NewConvNotificationData notificationData = new NewConvNotificationData("New comment added", convTitle);
		Notification notification = new Notification("New comment added", convTitle);
		SendData data = new SendData(notificationData, regId, notification);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.set("Authorization","key=" + System.getenv("AUTH_KEY"));
		HttpEntity<SendData> requestEntity = new HttpEntity<SendData>(data, requestHeaders);
		try{
			ResponseEntity<String> response = template.exchange(FIREBASE_URL, HttpMethod.POST, requestEntity, String.class);
			System.out.println(response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
