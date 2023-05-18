package com.example.demo.model.dtos;

import java.io.Serializable;

public class MovieInfoDTO  implements Serializable{

	private static final long serialVersionUID = -5883511436515486247L;

	private String message;
	private String title;
	
	
	public MovieInfoDTO() {
	}
	
	public MovieInfoDTO(String message, String title) {
		this.message = message;
		this.title = title;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}


