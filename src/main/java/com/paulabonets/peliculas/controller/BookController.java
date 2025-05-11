package com.paulabonets.peliculas.controller;

import com.paulabonets.peliculas.model.Book;
import com.paulabonets.peliculas.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book createdBook = bookService.save(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.update(id, book);

        return ResponseEntity.ok(updatedBook);
    }
}
