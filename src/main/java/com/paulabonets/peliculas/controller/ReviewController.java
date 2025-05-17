package com.paulabonets.peliculas.controller;

import com.paulabonets.peliculas.model.Content;
import com.paulabonets.peliculas.model.Review;
import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.records.ReviewRequest;
import com.paulabonets.peliculas.service.ContentService;
import com.paulabonets.peliculas.service.ReviewService;
import com.paulabonets.peliculas.service.UserService;
import com.paulabonets.peliculas.util.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private SessionHelper sessionHelper;

    @PostMapping
    public ResponseEntity<Review> createReview(@CookieValue(value = "session", required = true) String session, @RequestBody ReviewRequest request) {
        User user = sessionHelper.getUserFromSession(session);

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(user, request));
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id,  @CookieValue(value = "session", required = true) String session) {
        User user = sessionHelper.getUserFromSession(session);

        reviewService.deleteReview(id, user);
    }

    @GetMapping("/content/{id}")
    public List<Review> getContentUserReviews(@PathVariable Long id, @CookieValue(value = "session", required = true) String session) {
        sessionHelper.getUserFromSession(session);

        Content content = contentService.getById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found"));;

        return reviewService.getContentUserReviews(content);
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id, @CookieValue(value = "session", required = true) String session) {
        sessionHelper.getUserFromSession(session);

        return reviewService.getReview(id);
    }
}
