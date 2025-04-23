package com.ssnagin.collectionmanager.validation.validators;

public class NotEmptyCharSequenceValidator<T extends CharSequence> implements Validator<T> {
    public static final String EMPTY_VALUE_ERROR = "Empty value given";

    @Override
    public void validate(T value) {
        if (value.isEmpty())
            throw new IllegalArgumentException(EMPTY_VALUE_ERROR);
    }
}
