package com.example.demo.routers.exceptions;

import java.util.Map;

public interface RouterException {

	public Integer status();
	public Map<String, Object> messages();
	
}
