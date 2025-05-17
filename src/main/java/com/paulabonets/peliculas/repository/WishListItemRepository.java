package com.paulabonets.peliculas.repository;

import com.paulabonets.peliculas.model.Content;
import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.model.WishListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListItemRepository extends JpaRepository<WishListItem, Long> {
    List<WishListItem> findByUser(User user);
    boolean existsByUserAndContent(User user, Content content);
    void deleteByUserAndContent(User user, Content content);
}
