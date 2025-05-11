package com.paulabonets.peliculas.repository;

import com.paulabonets.peliculas.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
