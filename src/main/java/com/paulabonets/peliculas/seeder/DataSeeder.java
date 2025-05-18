package com.paulabonets.peliculas.seeder;

import com.paulabonets.peliculas.enums.TypeContent;
import com.paulabonets.peliculas.model.Book;
import com.paulabonets.peliculas.model.Movie;
import com.paulabonets.peliculas.repository.BookRepository;
import com.paulabonets.peliculas.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private BookRepository bookRepository;
    private MovieRepository movieRepository;

    public DataSeeder(BookRepository bookRepository, MovieRepository movieRepository) {
        this.bookRepository = bookRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) {
        if (bookRepository.count() == 0) {
            seedBooks();
        }

        if (movieRepository.count() == 0) {
            seedMovies();
        }
    }

    private void seedBooks() {
        bookRepository.saveAll(List.of(
                new Book("1984", "Distopía clásica de Orwell", parseDate("1949-06-08"), "Ciencia Ficción", "https://www.estudioenescarlata.com/media/img/portadas/_visd_0001JPG01MGM.jpg", TypeContent.BOOK, "George Orwell", "9780451524935"),
                new Book("El Principito", "Un clásico de Saint-Exupéry", parseDate("1943-04-06"), "Fábula", "https://th.bing.com/th/id/OIP.3J5Q7LzjrIA8dz31Dh64zQHaKT?rs=1&pid=ImgDetMain", TypeContent.BOOK, "Antoine de Saint-Exupéry", "9780156012195"),
                new Book("Cien años de soledad", "Obra maestra de García Márquez", parseDate("1967-06-05"), "Realismo mágico", "https://th.bing.com/th/id/OIP.qBIPFhlf2JT3pc0OpMEbpAHaLQ?rs=1&pid=ImgDetMain", TypeContent.BOOK, "Gabriel García Márquez", "9780307474728"),
                new Book("Fahrenheit 451", "Distopía de Bradbury", parseDate("1953-10-19"), "Ciencia Ficción", "https://imagessl1.casadellibro.com/a/l/t0/81/9788499895581.jpg", TypeContent.BOOK, "Ray Bradbury", "9781451673319"),
                new Book("Don Quijote", "Novela de Cervantes", parseDate("1605-01-16"), "Aventura", "https://i.pinimg.com/originals/b5/0f/9c/b50f9c7c4969883cfa1dfe5a80763529.jpg", TypeContent.BOOK, "Miguel de Cervantes", "9788491050257")
        ));
    }


    private void seedMovies() {
        movieRepository.saveAll(List.of(
                new Movie("Inception", "Sueños dentro de sueños", parseDate("2010-07-16"), "Ciencia Ficción", "https://cdn.shopify.com/s/files/1/1416/8662/products/inception_2010_imax_original_film_art_2000x.jpg?v=1551890318", TypeContent.MOVIE, "Christofer Nolan", 148),
                new Movie("Matrix", "Realidad simulada", parseDate("1999-03-31"), "Ciencia Ficción", "https://m.media-amazon.com/images/I/51EG732BV3L._AC_.jpg", TypeContent.MOVIE, "",136),
                new Movie("Interstellar", "Viajes interestelares", parseDate("2014-11-07"), "Ciencia Ficción", "https://m.media-amazon.com/images/I/91kFYg4fX3L._AC_SL1500_.jpg", TypeContent.MOVIE, "", 169),
                new Movie("Titanic", "Historia de amor en el Titanic", parseDate("1997-12-19"), "Drama/Romance", "https://c8.alamy.com/comp/PXNB80/titanic-original-movie-poster-PXNB80.jpg", TypeContent.MOVIE, "", 195),
                new Movie("El Padrino", "Historia de la mafia", parseDate("1972-03-24"), "Crimen/Drama", "https://www.udllibros.com/imagenes/9788418/978841818137.JPG", TypeContent.MOVIE, "", 175)
        ));
    }

    private Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
