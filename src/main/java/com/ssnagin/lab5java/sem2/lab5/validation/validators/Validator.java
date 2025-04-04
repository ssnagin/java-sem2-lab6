package com.ssnagin.lab5java.sem2.lab5.validation.validators;

public interface Validator<T> {
    void validate(T value);
}