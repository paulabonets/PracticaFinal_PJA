package com.paulabonets.peliculas.model;

import com.paulabonets.peliculas.enums.TypeContent;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Date release_date;
    private String genre;
    @Column(columnDefinition = "TEXT")
    private String imageBase64;

    @Enumerated(EnumType.STRING)
    private TypeContent type;

    public Content(String title, String description, Date release_date, String genre, String imageBase64, TypeContent type) {
        this.title = title;
        this.description = description;
        this.release_date = release_date;
        this.genre = genre;
        this.imageBase64 = imageBase64;
        this.type = type;
    }

    public Content() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public TypeContent getType() {
        return type;
    }

    public void setType(TypeContent type) {
        this.type = type;
    }
}
