package com.paulabonets.peliculas.model;

import com.paulabonets.peliculas.enums.TypeContent;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Movie extends Content {
    private String director;
    private Integer duration;

    public Movie(String title, String description, Date release_date, String genre, String imageUrl, TypeContent type, String director, Integer duration) {
        super(title, description, release_date, genre, imageUrl, type);

        this.director = director;
        this.duration = duration;
    }

    public Movie() {}

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
