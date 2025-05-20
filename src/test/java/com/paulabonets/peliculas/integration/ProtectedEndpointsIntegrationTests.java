package com.paulabonets.peliculas.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
public class ProtectedEndpointsIntegrationTests {

    private static final String EMAIL = "paula@test.com";
    private static final String NAME = "Paula";
    private static final String PASSWORD = "1234";

    @Autowired
    TestRestTemplate client;

    private HttpHeaders cookieHeaders;

    @BeforeEach
    void setup() {
        // 1. Registro
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String json = """
            {
              "name": "%s",
              "email": "%s",
              "password": "%s"
            }
            """.formatted(NAME, EMAIL, PASSWORD);

        client.exchange("http://localhost:8080/api/auth/register",
                HttpMethod.POST, new HttpEntity<>(json, headers), String.class);

        // 2. Login
        String loginJson = """
            {
              "email": "%s",
              "password": "%s"
            }
            """.formatted(EMAIL, PASSWORD);

        ResponseEntity<String> response = client.exchange("http://localhost:8080/api/auth/login",
                HttpMethod.POST, new HttpEntity<>(loginJson, headers), String.class);

        // 3. Captura la cookie de sesión
        String cookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        cookieHeaders = new HttpHeaders();
        cookieHeaders.add(HttpHeaders.COOKIE, cookie);
    }

    @Test
    public void getMe_returnsUserInfo() {
        ResponseEntity<String> response = client.exchange(
                "http://localhost:8080/api/auth/me",
                HttpMethod.GET,
                new HttpEntity<>(null, cookieHeaders),
                String.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().contains(EMAIL));
    }

    @Test
    public void logout_removesSession() {
        ResponseEntity<String> response = client.exchange(
                "http://localhost:8080/api/auth/logout",
                HttpMethod.POST,
                new HttpEntity<>(null, cookieHeaders),
                String.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getMe_withoutLogin_returnsUnauthorized() {
        // No incluimos la cookie de sesión
        ResponseEntity<String> response = client.exchange(
                "http://localhost:8080/api/auth/me",
                HttpMethod.GET,
                new HttpEntity<>(null, new HttpHeaders()), // sin cookies
                String.class
        );

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void logout_thenGetMe_returnsUnauthorized() {
        // 1. Hacemos logout
        client.exchange(
                "http://localhost:8080/api/auth/logout",
                HttpMethod.POST,
                new HttpEntity<>(null, cookieHeaders),
                String.class
        );

        // 2. Intentamos acceder a /me con la cookie antigua
        ResponseEntity<String> response = client.exchange(
                "http://localhost:8080/api/auth/me",
                HttpMethod.GET,
                new HttpEntity<>(null, cookieHeaders),
                String.class
        );

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void login_withCorrectCredentials_returnsSessionCookie() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String loginJson = """
    {
      "email": "%s",
      "password": "%s"
    }
    """.formatted(EMAIL, PASSWORD);

        HttpEntity<String> request = new HttpEntity<>(loginJson, headers);

        ResponseEntity<String> response = client.postForEntity(
                "http://localhost:8080/api/auth/login",
                request,
                String.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        String setCookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        Assertions.assertNotNull(setCookie);
        Assertions.assertTrue(setCookie.contains("session="));
    }


}
