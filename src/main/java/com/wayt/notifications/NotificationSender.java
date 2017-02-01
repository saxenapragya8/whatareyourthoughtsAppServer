package com.wayt.notifications;

import java.sql.SQLException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.wayt.dao.RegIdDao;
import com.wayt.responses.UpdateStatusResponse;

public class NotificationSender {

	private String FIREBASE_URL = "https://fcm.googleapis.com/fcm/send";
	
	public void sendNotification(Integer userId) throws ClassNotFoundException, SQLException{
		String regId = RegIdDao.getInstance().getRegId(userId);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(FIREBASE_URL)
		        // Add query parameter
		        .queryParam("userId", "3")
		        .queryParam("registrationId", "random");
		System.out.println(builder.toUriString());
		
		MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.set("", "");
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(mvm, requestHeaders);
//		ResponseEntity<RegIdUpdateResponse> response = template.exchange(builder.build().toUriString(), HttpMethod.POST, requestEntity, RegIdUpdateResponse.class);
//		RegIdUpdateResponse result = response.getBody();
//		System.out.println(result);
	}
}
