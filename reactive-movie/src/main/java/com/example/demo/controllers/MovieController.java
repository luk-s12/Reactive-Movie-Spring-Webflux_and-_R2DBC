package com.example.demo.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dtos.MovieDTO;
import com.example.demo.services.MovieService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/movies" )
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@GetMapping("/welcome")
	Mono<ResponseEntity<String>> message(){
		return this.movieService.welcomeMessage().map(ResponseEntity::ok);
	}
	
	@PostMapping
	public Mono<ResponseEntity<MovieDTO>> save( @Valid @RequestBody Mono<MovieDTO> request){
		return this.movieService.save(request).map(movie -> ResponseEntity.status(HttpStatus.CREATED).body(movie));
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<MovieDTO>> update(@PathVariable UUID id,  @Valid @RequestBody  Mono<MovieDTO> request){
		return this.movieService.update(id, request).map(movie -> ResponseEntity.status(HttpStatus.OK).body(movie));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<MovieDTO>> movie(@PathVariable UUID id){
		return this.movieService.movieById(id)
								.map(ResponseEntity::ok)
								.defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<MovieDTO> movies(){
		return this.movieService.movies();
	}
	
	@GetMapping(value="/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<MovieDTO> moviesSSE(){
		return this.movieService.moviesSSE();
	}
	
	@DeleteMapping("/{id}")
	public Mono< ResponseEntity<Void>> delete(@PathVariable UUID id){
		return this.movieService.deleteById(id).map(ResponseEntity::ok);
	}	
}
