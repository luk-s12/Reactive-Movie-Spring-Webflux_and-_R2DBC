package com.example.demo.services;

import java.util.UUID;

import com.example.demo.model.dtos.MovieDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieService {
	
	public Mono<String> welcomeMessage();
	public Mono<MovieDTO> save(Mono<MovieDTO> movieDTO);
	public Mono<MovieDTO> update(UUID uuid, Mono<MovieDTO> movieDTO);
	public Mono<MovieDTO> movieById(UUID id);
	public Flux<MovieDTO> movies();
	public Flux<MovieDTO> moviesSSE();
	public Mono<Void> deleteById(UUID id);
	
}
