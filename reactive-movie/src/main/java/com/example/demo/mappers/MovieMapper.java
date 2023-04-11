package com.example.demo.mappers;

import java.time.Instant;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.example.demo.model.dtos.MovieDTO;
import com.example.demo.model.entities.Movie;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovieMapper {

	MovieDTO toDto(Movie movie);	

	@Mapping(target = "createdAt", ignore = true)
	Movie toEntity(MovieDTO movieDTO);	
	
	@Mapping(target = "createdAt", ignore = true)
	Movie toUpdate(MovieDTO movieDTO, @MappingTarget Movie movie);	
	
	@BeforeMapping
	default void setCreatedAt(@MappingTarget Movie movie) {
			movie.setCreatedAt( Instant.now() );
	}
	

}
