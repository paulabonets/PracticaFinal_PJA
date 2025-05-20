package com.paulabonets.peliculas.service;

import com.paulabonets.peliculas.model.Movie;
import com.paulabonets.peliculas.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    public Movie save(Movie movie) {
        return repository.save(movie);
    }

    public Movie update(Long id, Movie movie) {
        return repository.findById(id)
            .map(existingMovie -> {
                existingMovie.setTitle(movie.getTitle());
                existingMovie.setDescription(movie.getDescription());
                existingMovie.setRelease_date(movie.getRelease_date());
                existingMovie.setGenre(movie.getGenre());
                existingMovie.setImageBase64(movie.getImageBase64());
                existingMovie.setDirector(movie.getDirector());
                existingMovie.setDuration(movie.getDuration());

                return repository.save(existingMovie);
        }).orElseThrow(() -> new EntityNotFoundException("Movie not founded: " + id));
    }
}
