package com.mhsoft.utils;

import org.json.JSONObject;

public class Utils {
	
	String message;
	String statusCode;
	
	final String MESSAGE = "message";
	final String STATUS_CODE= "status-code";
	
	
	public String JsonMessage (String message, String statusCode) {
		
	
		
		this.message = message;
		this.statusCode = statusCode;
		
		JSONObject jo = new JSONObject();
		jo.put(MESSAGE, message);
		jo.put(STATUS_CODE, statusCode);
		
		return jo.toString();
		
	}
	
   
    
 

}
