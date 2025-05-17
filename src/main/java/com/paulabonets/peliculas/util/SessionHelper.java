package com.paulabonets.peliculas.util;

import com.paulabonets.peliculas.model.User;
import com.paulabonets.peliculas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class SessionHelper {
    @Autowired
    private UserService userService;

    public User getUserFromSession(String session) {
        return userService.getUserBySession(session)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Session"));
    }
}
