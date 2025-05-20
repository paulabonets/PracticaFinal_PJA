package com.paulabonets.peliculas.controller;

import com.paulabonets.peliculas.enums.Rol;
import com.paulabonets.peliculas.model.Book;
import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.service.BookService;
import com.paulabonets.peliculas.util.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private SessionHelper sessionHelper;

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book, @CookieValue(value = "session", required = true) String session) {
        User user = sessionHelper.getUserFromSession(session);

        if (user.getRol() != Rol.ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only an admin can create content");
        }

        Book createdBook = bookService.save(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book, @CookieValue(value = "session", required = true) String session) {
        User user = sessionHelper.getUserFromSession(session);

        if (user.getRol() != Rol.ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only an admin can update content");
        }

        Book updatedBook = bookService.update(id, book);

        return ResponseEntity.ok(updatedBook);
    }
}
