package com.example.demo.controllers;

import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.example.demo.model.dtos.MovieDTO;
import com.example.demo.model.entities.Movie;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.utils.TimestampUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerTest {

	@Autowired
	private WebTestClient client;

	@Autowired
	private MovieRepository movieRepository;
	
	private MovieDTO movieDto;
	private Movie movieEntity;
    
	@BeforeEach
	public void setUp() {

		this.movieDto = new MovieDTO();
		this.movieDto.setTitle("Movie test");
		this.movieDto.setDescription("lorem");

		this.movieEntity =  new Movie();
		this.movieEntity.setTitle( movieDto.getTitle() );
		this.movieEntity.setDescription( movieDto.getDescription() );
		this.movieEntity.setCreatedAt( TimestampUtil.create() );
	}

	@Test
	@DisplayName("You should get the welcome message")
	public void welcome() {
		this.client.get()
				   .uri("/api/v1/movies/welcome")
				   .exchange().expectStatus().isOk()
				   .expectBody(String.class)
				   .isEqualTo("Welcome to the Asynchronous Movie API");
	}

	@Test
	@DisplayName("If I save the movie, then it returns the saved movie with the ID")
	public void save() {
		
		this.client.post()
				   .uri("/api/v1/movies")
	               .contentType(MediaType.APPLICATION_JSON)
	               .body(BodyInserters.fromValue(this.movieDto))
				   .exchange()
	               .expectStatus().isCreated()
	               .expectBody()
	               .jsonPath("id").isNotEmpty()
	               .jsonPath("title").isEqualTo( this.movieDto.getTitle() )
        		   .jsonPath("description").isEqualTo( this.movieDto.getDescription() );
	}

	@Test
	@DisplayName("If I modify the movie, then it should return the modified movie with the original creation date")
	public void update() {
		
		Movie movieResponse = this.movieRepository.save(movieEntity).block();
		
		this.client.put()
				   .uri("/api/v1/movies/{id}", movieResponse.getId() )
	               .contentType(MediaType.APPLICATION_JSON)
	               .body(BodyInserters.fromValue( this.movieDto) )
				   .exchange()
	               .expectStatus().isOk()
			       .expectBody(MovieDTO.class)
			       .value(response -> {
			            assertEquals( this.movieEntity.getTitle(), response.getTitle());
			            assertEquals( this.movieEntity.getDescription(), response.getDescription());
			        });

		this.movieRepository.findById( movieEntity.getId() )
		        			.as(StepVerifier::create)
		        			.assertNext(movie ->{  
					            assertEquals( this.movieEntity.getTitle(), movie.getTitle());
					            assertEquals( this.movieEntity.getDescription(), movie.getDescription());
		        				assertEquals( this.movieEntity.getCreatedAt(), movie.getCreatedAt());
		        			})
		        			.verifyComplete();

	}
	
	
	@Test
	@DisplayName("If I search for a specific movie, then it should return that particular one")
	public void findById() {

		Movie movieResponse = this.movieRepository.save( this.movieEntity).block();
		
		this.client.get()
				   .uri("/api/v1/movies/{id}", movieResponse.getId() )
				   .exchange()
	               .expectStatus().isOk()
			       .expectBody(MovieDTO.class)
			       .value(response -> {
			    	   	assertEquals( this.movieEntity.getId(), response.getId());
			            assertEquals( this.movieEntity.getTitle(), response.getTitle());
			            assertEquals( this.movieEntity.getDescription(), response.getDescription());
			        });

	}
	
	@Test
	@DisplayName("If I search for all the movies, then it should return the nine movies")
	public void movies() {
		this.client.get()
				   .uri("/api/v1/movies")
		           .accept(MediaType.TEXT_EVENT_STREAM)
				   .exchange()
	               .expectStatus().isOk()
	               .expectHeader().contentType("text/event-stream;charset=UTF-8")
	               .expectBodyList(MovieDTO.class)
	               .hasSize(9)
	               .value( movies -> {
	            	   assertFalse( movies.get(0).getId() == null );
	               });		
	}
	
	@Test
	@DisplayName("If I delete a movie, then it shouldn't exist")
	public void delete() {

		Movie movieResponse = this.movieRepository.save(movieEntity).block();
		
		this.client.delete()
				   .uri("/api/v1/movies/{id}", movieResponse.getId() )
				   .exchange()
	               .expectStatus().isOk()
                   .expectBody(Void.class);

	    this.movieRepository.findById(movieResponse.getId())
        					.as(StepVerifier::create)
        					.verifyComplete();
	}
	
}
