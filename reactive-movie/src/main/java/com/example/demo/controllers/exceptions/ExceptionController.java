package com.example.demo.controllers.exceptions;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import static org.springframework.http.ResponseEntity.badRequest;
import org.springframework.http.ResponseEntity;


import com.example.demo.rotuters.exceptions.ResponseException;
import com.example.demo.rotuters.exceptions.utils.ConstantUtil;


@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler
	public ResponseEntity< ResponseException > validateRequestHandler(WebExchangeBindException error ) {
		
		  List<FieldError> fieldErrors = error.getBindingResult().getFieldErrors(); 		  
		  Map<String, Object> messages =  new HashMap<>();
		  
		  for (FieldError fieldError : fieldErrors) {
		    String fieldName = fieldError.getField();
		    String errorMessage = fieldError.getDefaultMessage();
		    messages.put(fieldName, errorMessage);
		  }
		  
		  ResponseException response = new ResponseException();
		  response.setStatus(HttpStatus.BAD_REQUEST.value());
		  response.setErrors( List.of( messages ));

		  return  badRequest().body(response);
	}
	
	@ExceptionHandler
	public ResponseEntity< ? > goblalHandler(Exception error ) {
	
		  ResponseException response = new ResponseException();
		  response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		  response.setErrors( List.of( ConstantUtil.SERVER_ERROR ));

		  return  badRequest().body(response);
	}


}
