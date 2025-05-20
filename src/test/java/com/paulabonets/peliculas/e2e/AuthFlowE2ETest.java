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
public class AuthFlowE2ETest {

    private static final String EMAIL = "e2euser@mail.com";
    private static final String NAME = "E2E User";
    private static final String PASSWORD = "1234";

    @Autowired
    TestRestTemplate client;

    @Test
    public void testRegisterLoginAndGetMe() {
        // 1. Registro
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String registerJson = """
            {
              "name": "%s",
              "email": "%s",
              "password": "%s"
            }
            """.formatted(NAME, EMAIL, PASSWORD);

        ResponseEntity<String> registerResponse = client.exchange(
                "http://localhost:8080/api/auth/register",
                HttpMethod.POST,
                new HttpEntity<>(registerJson, headers),
                String.class
        );

        Assertions.assertEquals(HttpStatus.OK, registerResponse.getStatusCode());

        // 2. Login
        String loginJson = """
            {
              "email": "%s",
              "password": "%s"
            }
            """.formatted(EMAIL, PASSWORD);

        ResponseEntity<String> loginResponse = client.exchange(
                "http://localhost:8080/api/auth/login",
                HttpMethod.POST,
                new HttpEntity<>(loginJson, headers),
                String.class
        );

        Assertions.assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

        // 3. Capturar cookie
        String cookie = loginResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        HttpHeaders cookieHeaders = new HttpHeaders();
        cookieHeaders.add(HttpHeaders.COOKIE, cookie);

        // 4. Get Me
        ResponseEntity<String> meResponse = client.exchange(
                "http://localhost:8080/api/auth/me",
                HttpMethod.GET,
                new HttpEntity<>(null, cookieHeaders),
                String.class
        );

        Assertions.assertEquals(HttpStatus.OK, meResponse.getStatusCode());
        Assertions.assertTrue(meResponse.getBody().contains(EMAIL));
        Assertions.assertTrue(meResponse.getBody().contains(NAME));
    }
}
