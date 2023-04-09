package com.example.demo.rotuters.handlers;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface MovieHandler {

	public Mono<ServerResponse> welcomeMessage(ServerRequest request);
	public Mono<ServerResponse> save(ServerRequest request);
	public Mono<ServerResponse> movieById(ServerRequest request);
	public Mono<ServerResponse> movies(ServerRequest request);
	public Mono<ServerResponse> deleteById(ServerRequest request);
	
}
