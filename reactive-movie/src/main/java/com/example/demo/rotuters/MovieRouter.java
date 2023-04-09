package com.example.demo.rotuters;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.rotuters.exceptions.ConstraintException;
import com.example.demo.rotuters.handlers.MovieHandler;
import com.example.demo.utils.ErrorUtil;

@Configuration
public class MovieRouter {

	@Autowired
	private MovieHandler movieHandler;
	
	@Bean
	public RouterFunction<ServerResponse> movieApi(){
		return RouterFunctions
				.route()
				.path("/api/v2/movies", this::movieEndpoint)
				.build();
	}
	
	private RouterFunction<ServerResponse> movieEndpoint(){
		return RouterFunctions
				.route( )
				. GET("welcome", movieHandler::welcomeMessage)
				. POST("movie", accept(MediaType.APPLICATION_JSON), movieHandler::save)
				. GET("/movie/{id}", accept(MediaType.APPLICATION_JSON), movieHandler::movieById)
				. GET("", accept(MediaType.APPLICATION_JSON), movieHandler::movies)
				. DELETE("/movie/{id}", accept(MediaType.APPLICATION_JSON), movieHandler::deleteById)
				.onError(ConstraintException.class, ErrorUtil.customExceptionHandler())
				.onError(Exception.class, ErrorUtil.globalErrorHandler() )
				.build();			
	}
	

}
