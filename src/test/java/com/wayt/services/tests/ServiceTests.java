package com.wayt.services.tests;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.wayt.responses.AllDisplayDataResponse;
import com.wayt.responses.RegIdUpdateResponse;
import com.wayt.responses.UserAuthResponse;

public class ServiceTests {

	private final static String WAYT_SERVER = "https://evil-pumpkin-78760.herokuapp.com/rest/";
	private final static String WAYT_LOCAL = "http://localhost:8089/WhatAreYourThoughtsAppServer/rest/";
	
	public static void main(String args[]){
		RestTemplate template = new RestTemplate();
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
	    converters.add(new StringHttpMessageConverter());
	    converters.add(new Jaxb2RootElementHttpMessageConverter());
	   // converters.add(new MappingJackson2HttpMessageConverter());
	    converters.add(new MappingJackson2HttpMessageConverter());
	    template.setMessageConverters(converters);
	    
//	   isUser(template);
//	   addRegId(template);
//	    addFriends(template);
	    getAllDisplayData(template);
	}
	
	private static void getRegId(RestTemplate template) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(WAYT_SERVER + "regid/getregid")
		        // Add query parameter
		        .queryParam("userId", "3");
		System.out.println(builder.toUriString());
		
		MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(mvm, requestHeaders);
		ResponseEntity<String> response = template.exchange(builder.build().toUriString(), HttpMethod.POST, requestEntity, String.class);
		String result = response.getBody();
		System.out.println(result);
	}

	private static void addRegId(RestTemplate template) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(WAYT_LOCAL + "regid/updateregid")
		        // Add query parameter
		        .queryParam("userId", "3")
		        .queryParam("registrationId", "random");
		System.out.println(builder.toUriString());
		
		MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(mvm, requestHeaders);
		ResponseEntity<RegIdUpdateResponse> response = template.exchange(builder.build().toUriString(), HttpMethod.POST, requestEntity, RegIdUpdateResponse.class);
		RegIdUpdateResponse result = response.getBody();
		System.out.println(result);
	}

	public static void isUser(RestTemplate template){
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(WAYT_SERVER + "user/isuser")
		        // Add query parameter
		        .queryParam("username", "saxena.pragya8@gmail.com")
		        .queryParam("passwd", "pra20nav");
		System.out.println(builder.toUriString());
		
		MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(mvm, requestHeaders);
		ResponseEntity<UserAuthResponse> response = template.exchange(builder.build().toUriString(), HttpMethod.GET, requestEntity, UserAuthResponse.class);
		UserAuthResponse result = response.getBody();
		System.out.println(result);
	}
	
	public static void getAllFriends(RestTemplate template){
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(WAYT_LOCAL + "friendship/getfriends")
		        // Add query parameter
		        .queryParam("userId", 1);
		System.out.println(builder.toUriString());
		
		MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(mvm, requestHeaders);
		ResponseEntity<List> response = template.exchange(builder.build().toUriString(), HttpMethod.POST, requestEntity, List.class);
		List result = response.getBody();
		System.out.println(result);
	}
	
	public static void addFriends(RestTemplate template){
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(WAYT_LOCAL + "friendship/addfriend")
		        // Add query parameter
		        .queryParam("userId", 3)
		        .queryParam("email", "test@test.com");
		System.out.println(builder.toUriString());
		
		MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(mvm, requestHeaders);
		template.exchange(builder.build().toUriString(), HttpMethod.POST, requestEntity, Boolean.class);
//		List result = response.getBody();
//		System.out.println(result);
	}
	
	public static void getAllConvs(RestTemplate template){
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(WAYT_LOCAL + "conversations/getalluserconversations")
		        // Add query parameter
		        .queryParam("userId", 6);
		System.out.println(builder.toUriString());
		
		MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(mvm, requestHeaders);
		ResponseEntity<List> response = template.exchange(builder.build().toUriString(), HttpMethod.POST, requestEntity, List.class);
		List result = response.getBody();
		System.out.println(result);
	}
	
	public static void getAllDisplayData(RestTemplate template){
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(WAYT_SERVER + "displaydata/getdata")
		        // Add query parameter
		        .queryParam("usrId", 3);
		System.out.println(builder.toUriString());
		
		MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(mvm, requestHeaders);
		ResponseEntity<AllDisplayDataResponse> response = template.exchange(builder.build().toUriString(), HttpMethod.POST, requestEntity, AllDisplayDataResponse.class);
		AllDisplayDataResponse result = response.getBody();
		System.out.println(result.getParticipations());
		System.out.println(result.getConversations());
		System.out.println(result.getComments());
	}
}
