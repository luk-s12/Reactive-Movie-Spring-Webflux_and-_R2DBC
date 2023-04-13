package com.example.demo.mappers;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.example.demo.model.dtos.MovieDTO;
import com.example.demo.model.entities.Movie;
import com.example.demo.utils.TimestampUtil;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MovieMapper {

	MovieDTO toDto(Movie movie);	

	@Mapping(target = "createdAt", ignore = true)
	Movie toEntity(MovieDTO movieDTO);	
	
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "id", ignore = true)
	Movie toEntity(MovieDTO movieDTO, @MappingTarget Movie movie);	
	
	@BeforeMapping
	default void setCreatedAt(@MappingTarget Movie movie) {
		if(movie.getId() == null)
			movie.setCreatedAt( TimestampUtil.create() );
	}
	
}
