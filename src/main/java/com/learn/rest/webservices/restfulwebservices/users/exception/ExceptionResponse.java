package com.learn.rest.webservices.restfulwebservices.users.exception;

import java.util.Date;

public class ExceptionResponse {

	private Date timeStamp;
	public Date getTimeStamp() {
		return timeStamp;
	}
	public String getMessage() {
		return message;
	}
	public String getDetails() {
		return details;
	}
	public ExceptionResponse(Date timeStamp, String message, String details) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
	}
	private String message;
	private String details;
	
}
