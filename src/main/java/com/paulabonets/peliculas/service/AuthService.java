package com.paulabonets.peliculas.service;

import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public String authenticate(String username, String password) {
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            System.out.println("⚠️ Usuario no encontrado para el email: " + username);
            throw new RuntimeException("Usuario no encontrado");
        }

        if (!user.get().getPassword().equals(password)) {
            System.out.println("⚠️ Contraseña incorrecta para el usuario: " + username);
            throw new RuntimeException("Contraseña incorrecta");
        }

        System.out.println("✅ Usuario autenticado correctamente: " + username);
        return user.get().getRol().name();
    }


    public String getUserRole(String username) {
        return userRepository.findByEmail(username)
                .map(user -> user.getRol().name())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

}
