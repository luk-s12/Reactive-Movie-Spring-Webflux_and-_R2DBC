package com.example.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.dtos.MovieInfoDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@Configuration
public class MovieSinkConfig {

	@Bean
	public Many<MovieInfoDTO> sink() {
		return Sinks.many().replay().limit(1);
	}
	
	@Bean
	public Flux<MovieInfoDTO> broadcast(Many<MovieInfoDTO> sink) {
		return sink.asFlux();
	}	
}
