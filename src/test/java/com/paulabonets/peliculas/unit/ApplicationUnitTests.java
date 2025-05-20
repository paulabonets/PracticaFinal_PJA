package com.paulabonets.peliculas.unit;

import com.paulabonets.peliculas.enums.TypeContent;
import com.paulabonets.peliculas.model.Book;
import com.paulabonets.peliculas.model.Movie;
import com.paulabonets.peliculas.records.LoginRequest;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationUnitTests {

    @Test
    public void user_model_setsAndGetsCorrectly() {
        com.paulabonets.peliculas.model.User user = new com.paulabonets.peliculas.model.User();
        user.setName("Paula");
        user.setEmail("paula@test.com");
        user.setPassword("securepass");
        user.setRol(com.paulabonets.peliculas.enums.Rol.USER);
        user.setSession("abc123");

        assertEquals("Paula", user.getName());
        assertEquals("paula@test.com", user.getEmail());
        assertEquals("securepass", user.getPassword());
        assertEquals(com.paulabonets.peliculas.enums.Rol.USER, user.getRol());
        assertEquals("abc123", user.getSession());
    }

    @Test
    public void register_request_record_fields_work() {
        com.paulabonets.peliculas.records.RegisterRequest request = new com.paulabonets.peliculas.records.RegisterRequest("Ana", "ana@test.com", "pass");

        assertEquals("Ana", request.name());
        assertEquals("ana@test.com", request.email());
        assertEquals("pass", request.password());
    }

    @Test
    public void login_request_record_fields_work() {
        LoginRequest login = new LoginRequest("login@test.com", "1234");

        assertEquals("login@test.com", login.email());
        assertEquals("1234", login.password());
    }

    @Test
    public void book_model_setsFieldsCorrectly() {
        Book book = new Book("Book Title", "Desc", new Date(), "Sci-fi", "image64", TypeContent.BOOK, "Orwell", "9781234567890");

        assertEquals("Orwell", book.getAuthor());
        assertEquals("9781234567890", book.getIsbn());
        assertEquals("Book Title", book.getTitle());
        assertEquals(TypeContent.BOOK, book.getType());
    }

    @Test
    public void movie_model_setsFieldsCorrectly() {
        Movie movie = new Movie("Movie Title", "Plot", new Date(), "Drama", "img", TypeContent.MOVIE, "Nolan", 150);

        assertEquals("Nolan", movie.getDirector());
        assertEquals(150, movie.getDuration());
        assertEquals("Movie Title", movie.getTitle());
        assertEquals(TypeContent.MOVIE, movie.getType());
    }
}
