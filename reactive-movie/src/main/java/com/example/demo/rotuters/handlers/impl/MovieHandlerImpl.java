package com.example.demo.rotuters.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import java.util.UUID;

import com.example.demo.model.dtos.MovieDTO;
import com.example.demo.rotuters.handlers.MovieHandler;
import com.example.demo.services.MovieService;
import com.example.demo.utils.ErrorUtil;

import reactor.core.publisher.Mono;

@Component
public class MovieHandlerImpl implements MovieHandler {

	@Autowired
	private ErrorUtil errorUtil;

	
	@Autowired
	private MovieService movieService;

	@Override
	public Mono<ServerResponse> welcomeMessage(ServerRequest request) {
		return ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(this.movieService.welcomeMessage(), String.class);
	}

	@Override
	public Mono<ServerResponse> save(ServerRequest request) {
		Mono<MovieDTO> movieMono = request.bodyToMono(MovieDTO.class);
		return movieMono
				.flatMap( movie -> this.errorUtil.constraintViolation(movie) )
				.flatMap(movie -> {
			return status(HttpStatus.CREATED)
					.contentType(MediaType.APPLICATION_JSON)
					.body(this.movieService.save(movieMono), MovieDTO.class);
		});
	}

	@Override
	public Mono<ServerResponse> movieById(ServerRequest request) {
		UUID id = UUID.fromString(  request.pathVariable("id") );
		return this.movieService.movieById( id)
				.flatMap(response -> ok().contentType(MediaType.APPLICATION_JSON).body(  Mono.just(response), MovieDTO.class))
				.switchIfEmpty(ServerResponse.ok().bodyValue("There is no movie with the id: " + id));
	}

	@Override
	public Mono<ServerResponse> movies(ServerRequest request) {
		return ok().contentType(MediaType.TEXT_EVENT_STREAM).body( this.movieService.movies(), MovieDTO.class );
	}

	@Override
	public Mono<ServerResponse> deleteById(ServerRequest request) {
		UUID id = UUID.fromString(  request.pathVariable("id") );
		return this.movieService.deleteById(id)
				.flatMap(response -> ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(response), Void.class))
				.switchIfEmpty(ServerResponse.ok().bodyValue("There is no movie with the"+ id + "to delete"));

	}
}
