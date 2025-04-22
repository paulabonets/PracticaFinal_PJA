package com.paulabonets.peliculas.service;

import com.paulabonets.peliculas.model.Pelicula;
import com.paulabonets.peliculas.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository repository;

    public List<Pelicula> getAll() {
        return repository.findAll();
    }

    public Optional<Pelicula> getById(Long id) {
        return repository.findById(id);
    }

    public Pelicula save(Pelicula pelicula) {
        return repository.save(pelicula);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Pelicula update(Long id, Pelicula updated) {
        return repository.findById(id).map(p -> {
            p.setTitulo(updated.getTitulo());
            p.setAnio(updated.getAnio());
            p.setDirector(updated.getDirector());
            p.setSinopsis(updated.getSinopsis());
            p.setImagenUrl(updated.getImagenUrl());
            return repository.save(p);
        }).orElse(null);
    }
}
