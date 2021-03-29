package com.learn.rest.webservices.restfulwebservices.users.exception;


import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice // beacuse this will be shared across all the controllers
@RestController // beacuse this will be providing response to API call 
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException( Exception ex , WebRequest req)
	{
		ExceptionResponse exception =  new ExceptionResponse(new Date(), ex.getMessage(), req.getDescription(false));
		
		return new ResponseEntity<Object>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException( Exception ex , WebRequest req)
	{
		ExceptionResponse exception =  new ExceptionResponse(new Date(), ex.getMessage(), req.getDescription(false));
		
		return new ResponseEntity<Object>(exception, HttpStatus.NOT_FOUND);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionResponse exception =  new ExceptionResponse(new Date(), ex.getMessage(), ex.getBindingResult().toString());
		
		return new ResponseEntity<Object>(exception, HttpStatus.BAD_REQUEST);
		}
}
