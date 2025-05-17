package com.paulabonets.peliculas.service;
import com.paulabonets.peliculas.enums.Rol;
import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.repository.UserRepository;
import com.paulabonets.peliculas.util.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Hashing hashingUtil;

    public User register(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Este email ya se encuentra registrado");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(hashingUtil.hash(password));
        user.setRol(Rol.USER);
        user.setSession(UUID.randomUUID().toString());

        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        if (!hashingUtil.compare(user.getPassword(), password)) {
            throw new RuntimeException("La contraseña no coincide");
        }

        user.setSession(UUID.randomUUID().toString());

        return userRepository.save(user);
    }

    public Optional<User> getUserBySession(String session) {
        return userRepository.findBySession(session);
    }
}
