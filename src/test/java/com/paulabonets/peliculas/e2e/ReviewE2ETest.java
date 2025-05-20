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
public class ReviewE2ETest {

    @Autowired
    TestRestTemplate client;

    private final String EMAIL = "review@test.com";
    private final String NAME = "Reviewer";
    private final String PASSWORD = "1234";

    @Test
    public void createReviewAndGetItFromContent() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // REGISTRO
        String registerJson = """
            {
              "name": "%s",
              "email": "%s",
              "password": "%s"
            }
        """.formatted(NAME, EMAIL, PASSWORD);
        client.exchange("https://jpa-1-bo8z.onrender.com/api/auth/register", HttpMethod.POST, new HttpEntity<>(registerJson, headers), String.class);

        // LOGIN
        String loginJson = """
            {
              "email": "%s",
              "password": "%s"
            }
        """.formatted(EMAIL, PASSWORD);
        ResponseEntity<String> loginResponse = client.exchange("https://jpa-1-bo8z.onrender.com/api/auth/login", HttpMethod.POST, new HttpEntity<>(loginJson, headers), String.class);
        String cookie = loginResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.setContentType(MediaType.APPLICATION_JSON);
        authHeaders.add(HttpHeaders.COOKIE, cookie);

        // CREAR REVIEW (✅ CAMBIO AQUÍ: usamos reviewText en vez de description)
        String reviewJson = """
            {
              "stars": 4,
              "reviewText": "Una obra maestra distópica.",
              "contentId": 1
            }
        """;

        ResponseEntity<String> postResponse = client.exchange(
                "https://jpa-1-bo8z.onrender.com/api/reviews",
                HttpMethod.POST,
                new HttpEntity<>(reviewJson, authHeaders),
                String.class
        );
        Assertions.assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());

        // OBTENER REVIEWS
        ResponseEntity<String> getResponse = client.exchange(
                "https://jpa-1-bo8z.onrender.com/api/reviews/content/1",
                HttpMethod.GET,
                new HttpEntity<>(null, authHeaders),
                String.class
        );

        Assertions.assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        Assertions.assertNotNull(getResponse.getBody());
        Assertions.assertTrue(
                getResponse.getBody().contains("Una obra maestra distópica."),
                "La review no se encontró: " + getResponse.getBody()
        );
    }
}
