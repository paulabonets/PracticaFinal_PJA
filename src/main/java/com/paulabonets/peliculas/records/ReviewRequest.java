package com.paulabonets.peliculas.records;

import jakarta.validation.constraints.NotBlank;

public record ReviewRequest(
        @NotBlank
        Long contentId,
        @NotBlank
        String reviewText,
        @NotBlank
        int stars
) {}
