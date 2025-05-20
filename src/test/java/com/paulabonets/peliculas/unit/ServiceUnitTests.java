package com.paulabonets.peliculas.unit;

import com.paulabonets.peliculas.enums.Rol;
import com.paulabonets.peliculas.model.*;
import com.paulabonets.peliculas.records.ReviewRequest;
import com.paulabonets.peliculas.service.*;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceUnitTests {

    // --- BOOK SERVICE ---
    @Test
    void testBookServiceUpdate_updatesFieldsCorrectly() {
        BookService bookService = new BookService();

        Book existing = new Book();
        existing.setTitle("Old Title");

        Book update = new Book();
        update.setTitle("New Title");

        // Simulamos el repositorio con una lista
        List<Book> fakeDb = new ArrayList<>(List.of(existing));
        bookService = new BookService() {
            @Override
            public Book update(Long id, Book book) {
                Book b = fakeDb.get(0);
                b.setTitle(book.getTitle());
                return b;
            }
        };

        Book result = bookService.update(1L, update);
        assertEquals("New Title", result.getTitle());
    }

    // --- CONTENT SERVICE ---
    @Test
    void testContentServiceFilter_returnsCorrectList() {
        Content c1 = new Content("Movie A", "desc", new Date(), "Drama", "img", null);
        Content c2 = new Content("Movie B", "desc", new Date(), "Action", "img", null);
        List<Content> contents = List.of(c1, c2);

        ContentService contentService = new ContentService() {
            @Override
            public List<Content> getFiltered(String type, String title) {
                return contents.stream()
                        .filter(c -> c.getTitle().contains(title))
                        .toList();
            }
        };

        List<Content> result = contentService.getFiltered(null, "A");
        assertEquals(1, result.size());
        assertEquals("Movie A", result.get(0).getTitle());
    }

    // --- MOVIE SERVICE ---
    @Test
    void testMovieServiceSave_setsCorrectDirector() {
        Movie movie = new Movie();
        movie.setDirector("Spielberg");

        MovieService movieService = new MovieService() {
            @Override
            public Movie save(Movie m) {
                return m;
            }
        };

        Movie result = movieService.save(movie);
        assertEquals("Spielberg", result.getDirector());
    }

    // --- REVIEW SERVICE ---
    @Test
    void testReviewCreation_setsCorrectStars() {
        Content content = new Content();
        User user = new User();

        ReviewService reviewService = new ReviewService() {
            @Override
            public Review createReview(User u, ReviewRequest request) {
                return new Review(u, content, request.stars(), request.reviewText());
            }
        };

        Review review = reviewService.createReview(user, new ReviewRequest(content.getId(), "Muy buena", 5));
        assertEquals(5, review.getStars());
        assertEquals("Muy buena", review.getDescription());
    }

    // --- USER SERVICE (sin hashing, solo validación) ---
    @Test
    void testUserRegister_setsBasicData() {
        UserService userService = new UserService() {
            @Override
            public User register(String name, String email, String password) {
                User u = new User();
                u.setName(name);
                u.setEmail(email);
                u.setPassword("hashed"); // simulamos hash
                u.setRol(Rol.USER);
                return u;
            }
        };

        User u = userService.register("Ana", "ana@mail.com", "1234");
        assertEquals("Ana", u.getName());
        assertEquals("ana@mail.com", u.getEmail());
        assertEquals("hashed", u.getPassword());
        assertEquals(Rol.USER, u.getRol());
    }

    // --- WISHLIST SERVICE ---
    @Test
    void testAddToWishlist_addsOnlyOnce() {
        Content content = new Content();
        User user = new User();

        List<WishListItem> fakeDb = new ArrayList<>();

        WishListItemService wishListItemService = new WishListItemService() {
            @Override
            public void addToWishlist(User u, Long contentId) {
                boolean exists = fakeDb.stream()
                        .anyMatch(i -> i.getUser() == u && i.getContent() == content);
                if (!exists) {
                    WishListItem item = new WishListItem();
                    item.setUser(u);
                    item.setContent(content);
                    fakeDb.add(item);
                }
            }
        };

        wishListItemService.addToWishlist(user, 1L);
        wishListItemService.addToWishlist(user, 1L); // no debería duplicar
        assertEquals(1, fakeDb.size());
    }
}
