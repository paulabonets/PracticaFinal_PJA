package com.paulabonets.peliculas.service;

import com.paulabonets.peliculas.model.Content;
import com.paulabonets.peliculas.model.WishListItem;
import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.repository.ContentRepository;
import com.paulabonets.peliculas.repository.WishListItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishListItemService {
    @Autowired
    private WishListItemRepository wishListItemRepository;

    @Autowired
    private ContentRepository contentRepository;

    public List<Content> getWishListForUser(User user) {
        return wishListItemRepository.findByUser(user).stream()
                .map(WishListItem::getContent)
                .collect(Collectors.toList());

    }

    public void addToWishlist(User user, Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("El contenido no existe"));

        boolean exists = wishListItemRepository.existsByUserAndContent(user, content);

        if (!exists) {
            WishListItem item = new WishListItem();
            item.setUser(user);
            item.setContent(content);
            wishListItemRepository.save(item);
        }
    }

    @Transactional
    public void removeFromWishlist(User user, Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("El contenido no existe"));

        wishListItemRepository.deleteByUserAndContent(user, content);
    }
}
