package com.paulabonets.peliculas.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String value;
    private LocalDateTime expiration;
}
