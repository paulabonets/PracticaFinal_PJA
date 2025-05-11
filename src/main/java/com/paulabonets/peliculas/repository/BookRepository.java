package com.paulabonets.peliculas.repository;

import com.paulabonets.peliculas.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
