package com.paulabonets.peliculas.e2e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class WishlistE2ETest {

    @Autowired
    TestRestTemplate client;

    private final String EMAIL = "wishlist@test.com";
    private final String NAME = "Wishlist User";
    private final String PASSWORD = "1234";

    @Test
    public void addMovieToWishlistAndVerifyIt() {
        // --- REGISTRO ---
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String registerJson = """
        {
            "name": "%s",
            "email": "%s",
            "password": "%s"
        }
    """.formatted(NAME, EMAIL, PASSWORD);

        client.exchange("https://jpa-1-bo8z.onrender.com/api/auth/register",
                HttpMethod.POST,
                new HttpEntity<>(registerJson, headers),
                String.class);

        // --- LOGIN ---
        String loginJson = """
        {
            "email": "%s",
            "password": "%s"
        }
    """.formatted(EMAIL, PASSWORD);

        ResponseEntity<String> loginResponse = client.exchange("https://jpa-1-bo8z.onrender.com/api/auth/login",
                HttpMethod.POST,
                new HttpEntity<>(loginJson, headers),
                String.class);

        String sessionCookie = loginResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        HttpHeaders cookieHeaders = new HttpHeaders();
        cookieHeaders.add(HttpHeaders.COOKIE, sessionCookie);

        // --- AÑADIR A WISHLIST ---
        ResponseEntity<String> addResponse = client.exchange(
                "https://jpa-1-bo8z.onrender.com/api/wishlist/1",
                HttpMethod.POST,
                new HttpEntity<>(null, cookieHeaders),
                String.class
        );
        Assertions.assertEquals(HttpStatus.OK, addResponse.getStatusCode());

        // --- VERIFICAR WISHLIST ---
        ResponseEntity<String> wishlistResponse = client.exchange(
                "https://jpa-1-bo8z.onrender.com/api/wishlist",
                HttpMethod.GET,
                new HttpEntity<>(null, cookieHeaders),
                String.class
        );

        Assertions.assertEquals(HttpStatus.OK, wishlistResponse.getStatusCode());
        Assertions.assertNotNull(wishlistResponse.getBody());
        Assertions.assertTrue(wishlistResponse.getBody().contains("\"title\":\"1984\""),
                "La película no se encontró en la wishlist: " + wishlistResponse.getBody());
    }


}
