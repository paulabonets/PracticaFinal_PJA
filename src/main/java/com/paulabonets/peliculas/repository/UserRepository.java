package com.paulabonets.peliculas.repository;

import com.paulabonets.peliculas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySession(String session);

    @Query(value = "SELECT * FROM app_user WHERE email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);
}
