package com.paulabonets.peliculas.controller;

import com.paulabonets.peliculas.model.Content;
import com.paulabonets.peliculas.model.User;

import com.paulabonets.peliculas.service.UserService;
import com.paulabonets.peliculas.service.WishListItemService;
import com.paulabonets.peliculas.util.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishListItemController {
    @Autowired
    private WishListItemService wishListItemService;

    @Autowired
    private SessionHelper sessionHelper;


    @GetMapping
    public List<Content> getWishlist(@CookieValue("session") String session) {
        User user = sessionHelper.getUserFromSession(session);

        return wishListItemService.getWishListForUser(user);
    }

    @PostMapping("/{contentId}")
    public ResponseEntity<?> addToWishlist(@PathVariable Long contentId, @CookieValue("session") String session) {
        User user = sessionHelper.getUserFromSession(session);

        wishListItemService.addToWishlist(user, contentId);

        return ResponseEntity.status(200).body(null);
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable Long contentId, @CookieValue("session") String session) {
        User user = sessionHelper.getUserFromSession(session);

        wishListItemService.removeFromWishlist(user, contentId);

        return ResponseEntity.status(200).body(null);
    }
}
