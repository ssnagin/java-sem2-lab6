package com.ssnagin.collectionmanager.validation.validators;

import lombok.AllArgsConstructor;

import static org.apache.commons.lang3.compare.ComparableUtils.is;

@AllArgsConstructor
public class MaxValueValidator<T extends Comparable<T>> implements Validator<T> {
    private static final String MAX_VALUE_ERROR = "The value %s is higher than maximum %s";
    private T maximumValue;

    @Override
    public void validate(T value) {
        if (is(value).greaterThan(maximumValue))
            throw new IllegalArgumentException(String.format(MAX_VALUE_ERROR, value, maximumValue));
    }
}
