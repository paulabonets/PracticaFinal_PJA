package com.paulabonets.peliculas.model;

import jakarta.persistence.*;

@Entity

public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private int anio;
    private String director;
    private String sinopsis;
    private String imagenUrl;

    public Pelicula(String titulo, int anio, String director, String sinopsis, String imagenUrl) {
        this.titulo = titulo;
        this.anio = anio;
        this.director = director;
        this.sinopsis = sinopsis;
        this.imagenUrl = imagenUrl;
    }

    public Pelicula() {
        // Constructor vacío requerido por JPA
    }


    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

}
