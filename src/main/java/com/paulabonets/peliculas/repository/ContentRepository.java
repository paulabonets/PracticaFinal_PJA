package com.paulabonets.peliculas.repository;

import com.paulabonets.peliculas.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    @Query("SELECT content FROM Content content WHERE LOWER(content.type) = LOWER(:type)")
    List<Content> findByType(@Param("type") String type);

    @Query("SELECT content FROM Content content WHERE LOWER(content.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Content> findByTitle(@Param("title") String title);

    @Query("SELECT content FROM Content content WHERE LOWER(content.type) = LOWER(:type) AND LOWER(content.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Content> findByTypeAndTitle(@Param("type") String type, @Param("title") String title);
}
