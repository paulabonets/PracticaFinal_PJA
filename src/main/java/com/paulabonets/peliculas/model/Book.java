package com.paulabonets.peliculas.model;

import com.paulabonets.peliculas.enums.TypeContent;
import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class Book extends Content {
    private String author;
    private String isbn;

    public Book(String title, String description, Date release_date, String genre, String imageUrl, TypeContent type, String author, String isbn) {
        super(title, description, release_date, genre, imageUrl, type);

        this.author = author;
        this.isbn = isbn;
    }

    public Book() {}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
