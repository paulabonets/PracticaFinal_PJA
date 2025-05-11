package com.paulabonets.peliculas.service;

import com.paulabonets.peliculas.model.Content;
import com.paulabonets.peliculas.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentService {
    @Autowired
    private ContentRepository repository;

    public List<Content> getAll() {
        return repository.findAll();
    }

    public Optional<Content> getById(Long id) {
        return repository.findById(id);
    }

    public List<Content> getFiltered(String type, String title) {
        if (type != null && title != null) {
            return repository.findByTypeAndTitle(type, title);
        } else if (type != null) {
            return repository.findByType(type);
        } else if (title != null) {
            return repository.findByTitle(title);
        } else {
            return getAll();
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
