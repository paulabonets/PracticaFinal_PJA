package com.paulabonets.peliculas.controller;

import com.paulabonets.peliculas.model.Content;
import com.paulabonets.peliculas.service.ContentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    @GetMapping
    public List<Content> index(@RequestParam(required = false) String type,
                               @RequestParam(required = false) String title) {
        return contentService.getFiltered(type, title);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> show(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getById(id).orElse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contentService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
