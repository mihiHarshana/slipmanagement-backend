package com.mhsoft.utils;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

public class Utils {
	
	String message;
	HttpStatus statusCode;
	
	final String MESSAGE = "message";
	final String STATUS_CODE= "statusCode";
	
	
	public String JsonMessage (String message, HttpStatus statusCode) {
		
		this.message = message;
		this.statusCode = statusCode;
		
		JSONObject jo = new JSONObject();
		jo.put(MESSAGE, message);
		jo.put(STATUS_CODE, statusCode.value());
		
		return jo.toString();
		
	}
	
	
   
    
 

}
