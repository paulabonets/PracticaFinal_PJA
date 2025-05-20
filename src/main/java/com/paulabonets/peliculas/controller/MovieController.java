package com.paulabonets.peliculas.controller;

import com.paulabonets.peliculas.enums.Rol;
import com.paulabonets.peliculas.model.Movie;
import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.service.MovieService;
import com.paulabonets.peliculas.util.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private SessionHelper sessionHelper;

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie, @CookieValue(value = "session", required = true) String session) {
        User user = sessionHelper.getUserFromSession(session);

        if (user.getRol() != Rol.ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only an admin can create content");
        }

        Movie createdMovie = movieService.save(movie);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable Long id, @RequestBody Movie movie, @CookieValue(value = "session", required = true) String session) {
        User user = sessionHelper.getUserFromSession(session);

        if (user.getRol() != Rol.ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only an admin can update content");
        }

        Movie updatedMovie = movieService.update(id, movie);

        return ResponseEntity.ok(updatedMovie);
    }
}
