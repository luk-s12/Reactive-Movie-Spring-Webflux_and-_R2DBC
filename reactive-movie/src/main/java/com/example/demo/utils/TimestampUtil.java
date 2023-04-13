package com.example.demo.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class TimestampUtil {

	public static Instant create() {
		Instant now = Instant.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("UTC-3"));
		String formattedDate = formatter.format(now);
		return Instant.parse(formattedDate);
	}
	
}
