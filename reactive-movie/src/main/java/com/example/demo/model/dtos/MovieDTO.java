package com.example.demo.model.dtos;

import java.io.Serializable;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public class MovieDTO implements Serializable {

	private static final long serialVersionUID = -3485502077061969631L;

	private UUID id;

	@NotBlank
	private String title;

	@NotBlank
	private String description;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "MovieDTO [id=" + id + ", title=" + title + ", description=" + description + "]";
	}

}
