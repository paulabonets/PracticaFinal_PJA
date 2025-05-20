package com.paulabonets.peliculas.service;

import com.paulabonets.peliculas.enums.Rol;
import com.paulabonets.peliculas.model.Content;
import com.paulabonets.peliculas.model.Review;
import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.records.ReviewRequest;
import com.paulabonets.peliculas.repository.ContentRepository;
import com.paulabonets.peliculas.repository.ReviewRepository;
import com.paulabonets.peliculas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ContentRepository contentRepository;

    public Review createReview(User user, ReviewRequest request) {
        Content content = contentRepository.findById(request.contentId())
                .orElseThrow(() -> new RuntimeException("Content not found"));

        Review review = new Review(user, content, request.stars(), request.reviewText());

        return reviewRepository.save(review);
    }

    public List<Review> getContentUserReviews(Content content) {
        return reviewRepository.findByContent(content);
    }

    public Review getReview(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public void deleteReview(Long id, User user) {
        Review review;

        if (user.getRol() == Rol.ADMIN) {
            review = reviewRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Review not found"));
        } else {
            review = reviewRepository.findByIdAndUser(id, user)
                    .orElseThrow(() -> new RuntimeException("You cant not delete this review"));
        }

        reviewRepository.delete(review);
    }
}
