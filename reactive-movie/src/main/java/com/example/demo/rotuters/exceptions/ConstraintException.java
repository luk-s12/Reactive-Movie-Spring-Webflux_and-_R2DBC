package com.example.demo.rotuters.exceptions;

import java.util.Map;

public class ConstraintException extends RuntimeException implements RouterException {

	private static final long serialVersionUID = 9137136464826504931L;

	private Integer status;
	private Map<String, Object> messages;
	
	public ConstraintException() {
		super();
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setMessages(Map<String, Object> messages) {
		this.messages = messages;
	}

	@Override
	public Integer status() {
		return status;
	}

	@Override
	public Map<String, Object> messages() {
		return messages;
	}
}
