package com.paulabonets.peliculas.repository;

import com.paulabonets.peliculas.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

}
