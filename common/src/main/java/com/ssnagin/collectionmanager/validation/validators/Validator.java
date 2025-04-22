package com.ssnagin.collectionmanager.validation.validators;

public interface Validator<T> {
    void validate(T value);
}