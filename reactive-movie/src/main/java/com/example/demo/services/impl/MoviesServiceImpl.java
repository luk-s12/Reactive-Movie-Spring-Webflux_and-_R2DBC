package com.example.demo.services.impl;

import java.util.UUID;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mappers.MovieMapper;
import com.example.demo.model.dtos.MovieDTO;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.rotuters.exceptions.utils.ErrorUtil;
import com.example.demo.services.MovieService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MoviesServiceImpl implements MovieService {

	@Autowired
	private MovieMapper movieMapper;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ErrorUtil errorUtil;
	
	@Override
	public Mono<String> welcomeMessage() {
		return Mono.just("Welcome to the Asynchronous Movie API");
	}

	@Transactional
	@Override
	public Mono<MovieDTO> save(Mono<MovieDTO> movieMono) {
		return movieMono.flatMap( movie -> this.errorUtil.constraintViolation(movie) )
						.map(movieMapper::toEntity)
						.flatMap(movieRepository::save) 
						.map(movieMapper::toDto);
	}
	
	@Transactional
	@Override
	public Mono<MovieDTO> update(UUID uuid, Mono<MovieDTO> movieMono) {
		return this.movieRepository.findById(uuid)
								   .flatMap( movieEntity -> movieMono
										   					.map( movie -> this.movieMapper.toUpdate(movie, movieEntity))
										   					.doOnNext(movie -> movie.setId( movieEntity.getId() ) ))
								   .flatMap(movieRepository::save)
								   .map(this.movieMapper::toDto);
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public Mono<MovieDTO> movieById(UUID id) {
		return this.movieRepository
				   .findById(id)
				   .map(this.movieMapper::toDto);			
	}

	@Transactional(readOnly = true)
	@Override
	public Flux<MovieDTO> movies() {
		return this.movieRepository
				   .findAll()
				   .map(this.movieMapper::toDto);		   
	}

	@Transactional
	@Override
	public Mono<Void> deleteById(UUID id) {
		return this.movieRepository.deleteById(id);
	}

}
