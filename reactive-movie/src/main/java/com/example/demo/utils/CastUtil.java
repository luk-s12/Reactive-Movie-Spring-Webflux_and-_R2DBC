package com.example.demo.utils;

import java.util.UUID;

import reactor.core.publisher.Mono;

public final class CastUtil {

	public static Mono<UUID> toUUID(String uuid) {
		return 	Mono.fromSupplier(()->  UUID.fromString(uuid))
	            .onErrorMap(error -> new RuntimeException("Invalid ID provided. Please provide a valid ID"));
	}
}
