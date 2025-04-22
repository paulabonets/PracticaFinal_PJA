package com.paulabonets.peliculas.controller;

import com.paulabonets.peliculas.model.Pelicula;
import com.paulabonets.peliculas.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService service;

    @GetMapping
    public List<Pelicula> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Pelicula getById(@PathVariable Long id) {
        return service.getById(id).orElse(null);
    }

    @PostMapping
    public Pelicula create(@RequestBody Pelicula pelicula) {
        return service.save(pelicula);
    }

    @PutMapping("/{id}")
    public Pelicula update(@PathVariable Long id, @RequestBody Pelicula pelicula) {
        return service.update(id, pelicula);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
