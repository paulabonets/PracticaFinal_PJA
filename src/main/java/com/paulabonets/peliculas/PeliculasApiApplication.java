package com.paulabonets.peliculas;

import com.paulabonets.peliculas.model.Pelicula;
import com.paulabonets.peliculas.repository.PeliculaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PeliculasApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeliculasApiApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(PeliculaRepository repo) {
        return args -> {
            repo.save(new Pelicula("El Padrino", 1972, "Francis Ford Coppola", "Una historia de la mafia...", "https://www.udllibros.com/imagenes/9788418/978841818137.JPG"));
            repo.save(new Pelicula("Forrest Gump", 1994, "Robert Zemeckis", "La vida de Forest Gump", "https://image.tmdb.org/t/p/original/saHP97rTPS5eLmrLQEcANmKrsFl.jpg"));
            repo.save(new Pelicula("Inception", 2010, "Christopher Nolan", "La extraña habilidad de Cobb le ha convertido en un hombre muy cotizado en el mundo del espionaje", "https://cdn.shopify.com/s/files/1/1416/8662/products/inception_2010_imax_original_film_art_2000x.jpg?v=1551890318"));
        };
    }
}

