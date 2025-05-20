package com.paulabonets.peliculas.seeder;

import com.paulabonets.peliculas.enums.Rol;
import com.paulabonets.peliculas.enums.TypeContent;
import com.paulabonets.peliculas.model.Book;
import com.paulabonets.peliculas.model.Movie;
import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.repository.BookRepository;
import com.paulabonets.peliculas.repository.MovieRepository;
import com.paulabonets.peliculas.repository.UserRepository;
import com.paulabonets.peliculas.util.Hashing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private BookRepository bookRepository;
    private MovieRepository movieRepository;
    private UserRepository userRepository;
    private Hashing hashing;

    public DataSeeder(BookRepository bookRepository, MovieRepository movieRepository, Hashing hashing, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.hashing = hashing;
    }

    @Override
    public void run(String... args) {
        if (bookRepository.count() == 0) {
            seedBooks();
        }

        if (movieRepository.count() == 0) {
            seedMovies();
        }

        userRepository.save(new User("Admin", "admin@gmail.com", hashing.hash("admin123") ,Rol.ADMIN));
    }

    private void seedBooks() {
        bookRepository.saveAll(List.of(
                new Book("1984", "Distopía clásica de Orwell", parseDate("1949-06-08"), "Ciencia Ficción", encodeImageFromFile("1984.jpg"), TypeContent.BOOK, "George Orwell", "9780451524935"),
                new Book("El Principito", "Un clásico de Saint-Exupéry", parseDate("1943-04-06"), "Fábula", encodeImageFromFile("ElPrincipito.jpg"), TypeContent.BOOK, "Antoine de Saint-Exupéry", "9780156012195"),
                new Book("Cien años de soledad", "Obra maestra de García Márquez", parseDate("1967-06-05"), "Realismo mágico", encodeImageFromFile("CienAnios.jpg"), TypeContent.BOOK, "Gabriel García Márquez", "9780307474728"),
                new Book("Fahrenheit 451", "Distopía de Bradbury", parseDate("1953-10-19"), "Ciencia Ficción", encodeImageFromFile("fahrenheit.jpg"), TypeContent.BOOK, "Ray Bradbury", "9781451673319"),
                new Book("Don Quijote", "Novela de Cervantes", parseDate("1605-01-16"), "Aventura", encodeImageFromFile("quijote.jpg"), TypeContent.BOOK, "Miguel de Cervantes", "9788491050257"),
                new Book("Matar a un ruiseñor", "Drama racial en EE.UU.", parseDate("1960-07-11"), "Drama", encodeImageFromFile("rusienior.jpg"), TypeContent.BOOK, "Harper Lee", "9780061120084"),
                new Book("La Odisea", "Aventura épica de Homero", parseDate("800-01-01"), "Épica", encodeImageFromFile("odisea.jpg"), TypeContent.BOOK, "Homero", "9780140268867"),
                new Book("Crimen y castigo", "Psicología del crimen", parseDate("1866-01-01"), "Drama", encodeImageFromFile("crimen.jpg"), TypeContent.BOOK, "Fiódor Dostoyevski", "9788420674206"),
                new Book("El Hobbit", "Viaje de Bilbo Bolsón", parseDate("1937-09-21"), "Fantasía", encodeImageFromFile("hobbit.jpg"), TypeContent.BOOK, "J.R.R. Tolkien", "9780547928227"),
                new Book("Orgullo y prejuicio", "Romance de época", parseDate("1813-01-28"), "Romance", encodeImageFromFile("Orgullo.jpg"), TypeContent.BOOK, "Jane Austen", "9780141439518"),
                new Book("El nombre del viento", "Crónica de Kvothe", parseDate("2007-03-27"), "Fantasía", encodeImageFromFile("nombreViento.jpg"), TypeContent.BOOK, "Patrick Rothfuss", "9788401337208"),
                new Book("Los juegos del hambre", "Distopía juvenil", parseDate("2008-09-14"), "Ciencia Ficción", encodeImageFromFile("juegosHambre.jpg"), TypeContent.BOOK, "Suzanne Collins", "9780439023481"),
                new Book("El código Da Vinci", "Thriller religioso", parseDate("2003-03-18"), "Suspenso", encodeImageFromFile("davinci.jpg"), TypeContent.BOOK, "Dan Brown", "9780307474278"),
                new Book("Harry Potter y la piedra filosofal", "El inicio de la saga", parseDate("1997-06-26"), "Fantasía", encodeImageFromFile("harry.jpg"), TypeContent.BOOK, "J.K. Rowling", "9788478884452"),
                new Book("El alquimista", "Búsqueda personal", parseDate("1988-01-01"), "Fábula", encodeImageFromFile("alquimista.jpg"), TypeContent.BOOK, "Paulo Coelho", "9788408088882"),
                new Book("Las crónicas de Narnia", "Mundo mágico de Narnia", parseDate("1950-10-16"), "Fantasía", encodeImageFromFile("narnia.jpg"), TypeContent.BOOK, "C.S. Lewis", "9780064471190"),
                new Book("Drácula", "Clásico de vampiros", parseDate("1897-05-26"), "Terror", encodeImageFromFile("dracula.jpg"), TypeContent.BOOK, "Bram Stoker", "9780141439846"),
                new Book("Frankenstein", "El moderno Prometeo", parseDate("1818-01-01"), "Terror", encodeImageFromFile("frankeinstein.jpg"), TypeContent.BOOK, "Mary Shelley", "9788491050288"),
                new Book("La sombra del viento", "Barcelona literaria", parseDate("2001-06-06"), "Misterio", encodeImageFromFile("sombraViento.jpg"), TypeContent.BOOK, "Carlos Ruiz Zafón", "9788408055556"),
                new Book("Rayuela", "Narrativa experimental", parseDate("1963-06-28"), "Ficción", encodeImageFromFile("rayuela.jpg"), TypeContent.BOOK, "Julio Cortázar", "9788437600374")
        ));
    }

    private void seedMovies() {
        movieRepository.saveAll(List.of(
                new Movie("Inception", "Sueños dentro de sueños", parseDate("2010-07-16"), "Ciencia Ficción", encodeImageFromFile("1.jpg"), TypeContent.MOVIE, "Christopher Nolan", 148),
                new Movie("Matrix", "Realidad simulada", parseDate("1999-03-31"), "Ciencia Ficción", encodeImageFromFile("2.jpg"), TypeContent.MOVIE, "Hermanas Wachowski", 136),
                new Movie("Interstellar", "Viajes interestelares", parseDate("2014-11-07"), "Ciencia Ficción", encodeImageFromFile("3.jpg"), TypeContent.MOVIE, "Christopher Nolan", 169),
                new Movie("El Padrino", "Mafia e intriga familiar", parseDate("1972-03-24"), "Crimen", encodeImageFromFile("4.jpg"), TypeContent.MOVIE, "Francis Ford Coppola", 175),
                new Movie("Forrest Gump", "Vida extraordinaria", parseDate("1994-07-06"), "Drama", encodeImageFromFile("5.jpg"), TypeContent.MOVIE, "Robert Zemeckis", 142),
                new Movie("El club de la pelea", "Despertar masculino", parseDate("1999-10-15"), "Drama", encodeImageFromFile("6.jpg"), TypeContent.MOVIE, "David Fincher", 139),
                new Movie("El Señor de los Anillos", "Viaje épico por la Tierra Media", parseDate("2001-12-19"), "Fantasía", encodeImageFromFile("7.jpg"), TypeContent.MOVIE, "Peter Jackson", 178),
                new Movie("Pulp Fiction", "Historias cruzadas", parseDate("1994-10-14"), "Crimen", encodeImageFromFile("8.jpg"), TypeContent.MOVIE, "Quentin Tarantino", 154),
                new Movie("Gladiador", "Roma y venganza", parseDate("2000-05-05"), "Acción", encodeImageFromFile("9.jpg"), TypeContent.MOVIE, "Ridley Scott", 155),
                new Movie("Titanic", "Romance trágico", parseDate("1997-12-19"), "Romance", encodeImageFromFile("10.jpg"), TypeContent.MOVIE, "James Cameron", 195),
                new Movie("Avatar", "Pandora y el conflicto", parseDate("2009-12-18"), "Ciencia Ficción", encodeImageFromFile("11.jpg"), TypeContent.MOVIE, "James Cameron", 162),
                new Movie("Joker", "Origen del villano", parseDate("2019-10-04"), "Drama", encodeImageFromFile("12.jpg"), TypeContent.MOVIE, "Todd Phillips", 122),
                new Movie("Coco", "Tradiciones y familia", parseDate("2017-11-22"), "Animación", encodeImageFromFile("13.jpg"), TypeContent.MOVIE, "Lee Unkrich", 105),
                new Movie("Soul", "El sentido de la vida", parseDate("2020-12-25"), "Animación", encodeImageFromFile("14.jpg"), TypeContent.MOVIE, "Pete Docter", 100),
                new Movie("Toy Story", "Juguetes con vida", parseDate("1995-11-22"), "Animación", encodeImageFromFile("15.jpg"), TypeContent.MOVIE, "John Lasseter", 81),
                new Movie("Up", "Aventura aérea", parseDate("2009-05-29"), "Animación", encodeImageFromFile("16.jpg"), TypeContent.MOVIE, "Pete Docter", 96),
                new Movie("WALL·E", "Robot con sentimientos", parseDate("2008-06-27"), "Animación", encodeImageFromFile("17.jpg"), TypeContent.MOVIE, "Andrew Stanton", 98),
                new Movie("La La Land", "Romance y jazz", parseDate("2016-12-09"), "Musical", encodeImageFromFile("18.jpg"), TypeContent.MOVIE, "Damien Chazelle", 128),
                new Movie("Whiplash", "Obsesión por la perfección", parseDate("2014-10-10"), "Drama", encodeImageFromFile("19.jpg"), TypeContent.MOVIE, "Damien Chazelle", 107),
                new Movie("El Gran Hotel Budapest", "Aventuras de conserjes", parseDate("2014-03-28"), "Comedia", encodeImageFromFile("20.jpg"), TypeContent.MOVIE, "Wes Anderson", 99)
        ));
    }


    private Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String encodeImageFromFile(String relativePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("images/" + relativePath)) {
            if (is == null) {
                throw new RuntimeException("No se pudo encontrar la imagen " + relativePath + " en el classpath");
            }
            byte[] bytes = is.readAllBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer la imagen " + relativePath, e);
        }
    }

}
