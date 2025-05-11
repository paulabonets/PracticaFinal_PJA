package com.paulabonets.peliculas;

import com.paulabonets.peliculas.model.Movie;
import com.paulabonets.peliculas.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MoviesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesApiApplication.class, args);
    }
}

