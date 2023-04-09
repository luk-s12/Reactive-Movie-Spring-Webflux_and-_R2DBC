package com.example.demo.rotuters.exceptions;

import java.util.Map;

public interface RouterException {

	public Integer status();
	public Map<String, Object> messages();
	
}
