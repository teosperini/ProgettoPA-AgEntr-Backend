package com.github.teosperini.progetto_pa.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String codiceFiscale) {
        super("L'utente con codice fiscale " + codiceFiscale + " non esiste");
    }
}
