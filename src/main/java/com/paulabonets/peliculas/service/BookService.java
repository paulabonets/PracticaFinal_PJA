package com.paulabonets.peliculas.service;

import com.paulabonets.peliculas.model.Book;
import com.paulabonets.peliculas.model.Movie;
import com.paulabonets.peliculas.repository.BookRepository;
import com.paulabonets.peliculas.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public Book save(Book book) {
        return repository.save(book);
    }

    public Book update(Long id, Book book) {
        return repository.findById(id)
            .map(existingMovie -> {
                existingMovie.setTitle(book.getTitle());
                existingMovie.setDescription(book.getDescription());
                existingMovie.setRelease_date(book.getRelease_date());
                existingMovie.setGenre(book.getGenre());
                existingMovie.setImageBase64(book.getImageBase64());
                existingMovie.setAuthor(book.getAuthor());
                existingMovie.setIsbn(book.getIsbn());

                return repository.save(existingMovie);
        }).orElseThrow(() -> new EntityNotFoundException("Book not founded: " + id));
    }
}
