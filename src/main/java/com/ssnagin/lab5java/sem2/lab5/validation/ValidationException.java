package com.ssnagin.lab5java.sem2.lab5.validation;

public class ValidationException extends RuntimeException {
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}