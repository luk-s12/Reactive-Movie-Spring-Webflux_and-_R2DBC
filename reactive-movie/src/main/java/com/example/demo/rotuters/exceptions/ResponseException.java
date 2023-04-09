package com.example.demo.rotuters.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ResponseException {

	private Integer status;
	private List<Object> errors;
	
	public ResponseException() {
		this.errors = new ArrayList<>();
	}

	public ResponseException(Integer status, List<Object> errors) {
		this.status = status;
		this.errors = errors;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Object> getErrors() {
		return errors;
	}

	public void setErrors(List<Object> errors) {
		this.errors = errors;
	}
}
