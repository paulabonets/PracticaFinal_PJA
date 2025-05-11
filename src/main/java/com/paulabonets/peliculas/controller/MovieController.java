package com.paulabonets.peliculas.controller;

import com.paulabonets.peliculas.model.Movie;
import com.paulabonets.peliculas.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        Movie createdMovie = movieService.save(movie);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable Long id, @RequestBody Movie movie) {
        Movie updatedMovie = movieService.update(id, movie);

        return ResponseEntity.ok(updatedMovie);
    }
}
