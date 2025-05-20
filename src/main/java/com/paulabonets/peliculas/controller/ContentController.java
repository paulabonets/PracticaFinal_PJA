package com.paulabonets.peliculas.controller;

import com.paulabonets.peliculas.enums.Rol;
import com.paulabonets.peliculas.model.Content;
import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.service.ContentService;
import com.paulabonets.peliculas.util.SessionHelper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    @Autowired
    private SessionHelper sessionHelper;

    @GetMapping
    public List<Content> index(@RequestParam(required = false) String type,
                               @RequestParam(required = false) String title,
                               @CookieValue(value = "session", required = true) String session) {
        return contentService.getFiltered(type, title);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> show(@PathVariable Long id, @CookieValue(value = "session", required = true) String session) {
        return ResponseEntity.ok(contentService.getById(id).orElse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @CookieValue(value = "session", required = true) String session) {
        User user = sessionHelper.getUserFromSession(session);

        if (user.getRol() != Rol.ADMIN) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only an admin can delete content");
        }

        contentService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
