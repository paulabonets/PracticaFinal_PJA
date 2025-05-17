package com.paulabonets.peliculas.controller;

import com.paulabonets.peliculas.enums.Rol;
import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.records.LoginRequest;
import com.paulabonets.peliculas.records.RegisterRequest;
import com.paulabonets.peliculas.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRol(Rol.valueOf(request.rol())); // Asignamos el rol que llega en el Request

        authService.saveUser(user);

        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            String role = authService.authenticate(request.email(), request.password());

            Cookie cookie = new Cookie("session", role);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            // ✅ Devuelvo un JSON en lugar de un String
            return ResponseEntity.ok(Map.of(
                    "message", "Login exitoso",
                    "role", role,
                    "token", "token_de_prueba"  // Aquí deberías devolver un token real si usas JWT
            ));
        } catch (RuntimeException e) {
            System.out.println("⚠️ Error en Login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "error", "Error: " + e.getMessage()
            ));
        }
    }

}
