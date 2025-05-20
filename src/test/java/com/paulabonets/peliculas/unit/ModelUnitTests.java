package com.paulabonets.peliculas.unit;

import com.paulabonets.peliculas.enums.Rol;
import com.paulabonets.peliculas.enums.TypeContent;
import com.paulabonets.peliculas.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ModelUnitTests {

    @Test
    void testBookModel() {
        Book book = new Book("Title", "Desc", new Date(), "Genre", "image", TypeContent.BOOK, "Author", "123456");
        assertEquals("Author", book.getAuthor());
        assertEquals("123456", book.getIsbn());
    }

    @Test
    void testContentModel() {
        Content content = new Content("Title", "Desc", new Date(), "Genre", "img64", TypeContent.MOVIE);
        assertEquals("Title", content.getTitle());
        assertEquals(TypeContent.MOVIE, content.getType());
    }

    @Test
    void testMovieModel() {
        Movie movie = new Movie("Movie", "Desc", new Date(), "Genre", "img", TypeContent.MOVIE, "Director", 120);
        assertEquals("Director", movie.getDirector());
        assertEquals(120, movie.getDuration());
    }

    @Test
    void testReviewModel() {
        User user = new User();
        Content content = new Content();
        Review review = new Review(user, content, 4, "Good!");
        assertEquals(4, review.getStars());
        assertEquals("Good!", review.getDescription());
        assertEquals(user, review.getUser());
    }

    @Test
    void testUserModel() {
        User user = new User();
        user.setName("Paula");
        user.setEmail("paula@example.com");
        user.setPassword("1234");
        user.setRol(Rol.USER);

        assertEquals("Paula", user.getName());
        assertEquals("paula@example.com", user.getEmail());
        assertEquals(Rol.USER, user.getRol());
    }

    @Test
    void testWishListItemModel() {
        User user = new User();
        Content content = new Content();
        WishListItem item = new WishListItem();
        item.setUser(user);
        item.setContent(content);
        item.setCreatedAt(LocalDate.now());

        assertEquals(user, item.getUser());
        assertEquals(content, item.getContent());
        assertNotNull(item.getCreatedAt());
    }
}
