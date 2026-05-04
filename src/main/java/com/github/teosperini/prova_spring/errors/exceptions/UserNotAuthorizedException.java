package com.github.teosperini.prova_spring.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotAuthorizedException extends RuntimeException {
    public UserNotAuthorizedException(String username) {
        super ("Utente " + username + " non autorizzato");
    }
}
