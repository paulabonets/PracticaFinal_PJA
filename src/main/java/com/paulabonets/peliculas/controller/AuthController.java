package com.paulabonets.peliculas.controller;

import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.records.LoginRequest;
import com.paulabonets.peliculas.records.RegisterRequest;
import com.paulabonets.peliculas.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, HttpServletResponse response) {
        User user = userService.register(request.name(), request.email(), request.password());

        Cookie cookie = new Cookie("session", user.getSession());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        User user = userService.login(request.email(), request.password());

        Cookie cookie = new Cookie("session", user.getSession());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok("Login exitoso");
    }
}

