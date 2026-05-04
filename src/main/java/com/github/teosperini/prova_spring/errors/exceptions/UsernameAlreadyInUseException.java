package com.github.teosperini.prova_spring.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException(String username){
        super("L'username " + username + " è già in uso");
    }
}
