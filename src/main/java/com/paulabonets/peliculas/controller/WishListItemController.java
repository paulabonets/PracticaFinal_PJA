package com.paulabonets.peliculas.controller;

import com.paulabonets.peliculas.model.Content;
import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.model.WishListItem;
import com.paulabonets.peliculas.repository.WishListItemRepository;
import com.paulabonets.peliculas.service.MovieService;
import com.paulabonets.peliculas.service.UserService;
import com.paulabonets.peliculas.service.WishListItemService;
import org.aspectj.lang.annotation.RequiredTypes;
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
    private UserService userService;

    private User getUserFromSession(String session) {
        return userService.getUserBySession(session)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "La sesión no es válida"));
    }

    private List<Content> getWishList(@CookieValue(value = "session", required = true) String session) {
        User user = userService.getUserBySession(session)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sesión inválida"));

        return wishListItemService.getWishListForUser(user);
    }

    @GetMapping
    public List<Content> getWishlist(@CookieValue("session") String session) {
        User user = getUserFromSession(session);

        return wishListItemService.getWishListForUser(user);
    }

    @PostMapping("/{contentId}")
    public ResponseEntity<?> addToWishlist(@PathVariable Long contentId, @CookieValue("session") String session) {
        User user = getUserFromSession(session);

        wishListItemService.addToWishlist(user, contentId);

        return ResponseEntity.status(200).body(null);
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<?> removeFromWishlist(@PathVariable Long contentId, @CookieValue("session") String session) {
        User user = getUserFromSession(session);

        wishListItemService.removeFromWishlist(user, contentId);

        return ResponseEntity.status(200).body(null);
    }
}
