package com.example.demo.routers.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;


import com.example.demo.model.dtos.MovieDTO;
import com.example.demo.model.dtos.MovieInfoDTO;
import com.example.demo.routers.exceptions.utils.ErrorUtil;
import com.example.demo.routers.handlers.MovieHandler;
import com.example.demo.services.MovieService;
import com.example.demo.utils.CastUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MovieHandlerImpl implements MovieHandler {

	@Autowired
	private ErrorUtil errorUtil;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private Flux<MovieInfoDTO> moviInfoLog;
	
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
				.map(	  movie ->	this.movieService.save( Mono.just(movie)  ))
				.flatMap( movie ->	status(HttpStatus.CREATED)
									.contentType(MediaType.APPLICATION_JSON)
									.body( movie, MovieDTO.class) );
	}
	@Override
	public Mono<ServerResponse> update(ServerRequest request) {	
		Mono<MovieDTO> movieMono = request.bodyToMono(MovieDTO.class);
		String id = request.pathVariable("id") ;
		return CastUtil.toUUID(id)
				.flatMap( uuid ->  this.movieService.update(uuid, movieMono))
				.flatMap( movie -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(movie) )
				.switchIfEmpty( notFound().build() );
	}
	
	@Override
	public Mono<ServerResponse> movieById(ServerRequest request) {
		String id = request.pathVariable("id") ;
		return  CastUtil.toUUID(id)
				.flatMap(this.movieService::movieById)
				.flatMap(response -> ok().contentType(MediaType.APPLICATION_JSON).body(  Mono.just(response), MovieDTO.class))
				.switchIfEmpty( ok().bodyValue("There is no movie with the id: " + id));
	}

	@Override
	public Mono<ServerResponse> movies(ServerRequest request) {
		return ok().body( this.movieService.movies(), MovieDTO.class );
	}

	@Override
	public Mono<ServerResponse> moviesSSE(ServerRequest request) {
		return ok().contentType(MediaType.TEXT_EVENT_STREAM).body( this.movieService.moviesSSE(), MovieDTO.class );

	}
	@Override
	public Mono<ServerResponse> moviesSink(ServerRequest request) {
		return ok().contentType(MediaType.TEXT_EVENT_STREAM).body( this.moviInfoLog, MovieDTO.class );
	}

	
	@Override
	public Mono<ServerResponse> deleteById(ServerRequest request) {
		String id = request.pathVariable("id") ;
		return  CastUtil.toUUID(id)
				.flatMap(this.movieService::deleteById)				
				.then( noContent().build() );
	}
}
