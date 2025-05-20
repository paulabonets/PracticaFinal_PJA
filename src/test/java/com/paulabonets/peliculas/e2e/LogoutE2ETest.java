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
public class LogoutE2ETest {

    @Autowired
    TestRestTemplate client;

    private final String EMAIL = "logout@test.com";
    private final String NAME = "Logout User";
    private final String PASSWORD = "1234";

    @Test
    public void logoutThenAccessProtectedEndpoint_shouldReturnUnauthorized() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // --- REGISTRO ---
        String registerJson = """
            {
                "name": "%s",
                "email": "%s",
                "password": "%s"
            }
        """.formatted(NAME, EMAIL, PASSWORD);

        client.exchange("http://localhost:8080/api/auth/register",
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

        ResponseEntity<String> loginResponse = client.exchange("http://localhost:8080/api/auth/login",
                HttpMethod.POST,
                new HttpEntity<>(loginJson, headers),
                String.class);

        String sessionCookie = loginResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        HttpHeaders cookieHeaders = new HttpHeaders();
        cookieHeaders.add(HttpHeaders.COOKIE, sessionCookie);

        // --- LOGOUT ---
        ResponseEntity<String> logoutResponse = client.exchange("http://localhost:8080/api/auth/logout",
                HttpMethod.POST,
                new HttpEntity<>(null, cookieHeaders),
                String.class);
        Assertions.assertEquals(HttpStatus.OK, logoutResponse.getStatusCode());

        // --- INTENTO DE ACCESO TRAS LOGOUT ---
        ResponseEntity<String> meResponse = client.exchange("http://localhost:8080/api/auth/me",
                HttpMethod.GET,
                new HttpEntity<>(null, cookieHeaders),
                String.class);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, meResponse.getStatusCode(),
                "Después del logout, el acceso a /me debería estar denegado.");
    }
}
