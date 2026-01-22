package com.backend.springboot.tickets.exception;

public class DuplicateEmailDeveloperException extends RuntimeException {
    public DuplicateEmailDeveloperException(String message) {
        super(message);
    }
}
