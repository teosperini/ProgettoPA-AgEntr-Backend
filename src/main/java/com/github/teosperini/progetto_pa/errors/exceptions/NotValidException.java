package com.github.teosperini.progetto_pa.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidException extends RuntimeException {
    public NotValidException(String codiceFiscale) {
        super(codiceFiscale);
    }
}
