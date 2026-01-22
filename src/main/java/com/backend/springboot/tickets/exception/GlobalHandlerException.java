package com.backend.springboot.tickets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handlerMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> mensaje = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    mensaje.put(error.getField(), error.getDefaultMessage());
                });

        return mensaje;
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handlerSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Violación de restricción de integridad: " + sqlIntegrityConstraintViolationException.getMessage());

        return mensaje;
    }

    @ExceptionHandler({DuplicateEmailDeveloperException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handlerDuplicateEmailDeveloperException(DuplicateEmailDeveloperException duplicateEmailDeveloperException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", " Recurso no registrado: " + duplicateEmailDeveloperException.getMessage());

        return mensaje;
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handlerResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", " Recurso no encontrado: " + resourceNotFoundException.getMessage());

        return mensaje;
    }




}
