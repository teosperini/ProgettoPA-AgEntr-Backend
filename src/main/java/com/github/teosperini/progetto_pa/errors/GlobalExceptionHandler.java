package com.github.teosperini.progetto_pa.errors;

import com.github.teosperini.progetto_pa.errors.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CFAlreadyInUseException.class)
    public ResponseEntity<Map<String, String>> handleConflict(CFAlreadyInUseException ex){
        return createResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(UserNotFoundException ex) {
        return createResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorized(UserNotAuthorizedException ex) {
        return createResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(Exception ex) {
        return createResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, String>> createResponse(String message, HttpStatus status){
        Map<String, String> error = new HashMap<>();
        error.put("message", message);
        error.put("status", String.valueOf(status.value()));
        return new ResponseEntity<>(error, status);
    }
}
