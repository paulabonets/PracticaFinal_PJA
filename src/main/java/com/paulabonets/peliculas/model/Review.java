package com.paulabonets.peliculas.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.paulabonets.peliculas.model.Content;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    private Integer stars;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDate created_at;

    public Review(User user, Content content, Integer stars, String description) {
        this.user = user;
        this.content = content;
        this.stars = stars;
        this.description = description;
        this.created_at = LocalDate.now();
    }

    public Review() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
}
