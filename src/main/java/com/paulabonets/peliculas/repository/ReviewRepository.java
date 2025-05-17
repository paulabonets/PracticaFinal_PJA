package com.paulabonets.peliculas.repository;

import com.paulabonets.peliculas.model.Review;
import com.paulabonets.peliculas.model.Content;
import com.paulabonets.peliculas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByContent(Content content);
    List<Review> findByUser(User user);
    Optional<Review> findByIdAndUser(Long id, User user);
}
