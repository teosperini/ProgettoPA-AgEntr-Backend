package com.github.teosperini.progetto_pa.errors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CFAlreadyInUseException extends RuntimeException {
    public CFAlreadyInUseException(String codiceFiscale) {
        super("L'username " + codiceFiscale + " è già in uso");
    }
}
