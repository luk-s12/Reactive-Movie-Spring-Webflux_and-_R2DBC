package com.example.demo.repositories;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entities.Movie;

@Repository
public interface MovieRepository extends ReactiveCrudRepository<Movie, UUID> {
}
