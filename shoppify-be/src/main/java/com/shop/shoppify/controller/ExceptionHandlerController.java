package com.shop.shoppify.controller;

import com.shop.shoppify.util.ShoppifyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) throws IOException {
        return buildResponseEntity(new ShoppifyException(HttpStatus.FORBIDDEN, e));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e) throws IOException {
        return buildResponseEntity(new ShoppifyException(HttpStatus.FORBIDDEN, e));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleEntityExistsException(EntityExistsException e) {
        return buildResponseEntity(new ShoppifyException(HttpStatus.BAD_REQUEST, e));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleAuthenticationServiceException(EntityNotFoundException e) {
        return buildResponseEntity(new ShoppifyException(HttpStatus.NOT_FOUND, e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        HashMap<String, List<String>> validationErrors = new HashMap<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            if (validationErrors.get(error.getField()) == null) {
                List<String> validationErrorMessages = new ArrayList<>();
                validationErrorMessages.add(error.getDefaultMessage());
                validationErrors.put(error.getField(), validationErrorMessages);
                continue;
            }

            validationErrors.get(error.getField()).add(error.getDefaultMessage());
        }

        return buildResponseEntity(new ShoppifyException() {
            private static final long serialVersionUID = 1L;

            {
                setStatus(HttpStatus.BAD_REQUEST);
                setValidationErrors(validationErrors);
                setMessage("Input values are invalid.");
            }
        });
    }

    @ExceptionHandler(ShoppifyException.class)
    public ResponseEntity<?> handleShoppifyException(ShoppifyException e) {
        return buildResponseEntity(e);
    }

    private ResponseEntity<?> buildResponseEntity(ShoppifyException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }
}
