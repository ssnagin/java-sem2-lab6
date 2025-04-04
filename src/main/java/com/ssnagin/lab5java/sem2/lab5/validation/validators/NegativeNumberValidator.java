package com.ssnagin.lab5java.sem2.lab5.validation.validators;

import static org.apache.commons.lang3.compare.ComparableUtils.is;

public class NegativeNumberValidator<T extends Number & Comparable<T>> implements Validator<T> {
    private static final String POSITIVE_NUMBER_ERROR = "The number %s is positive";

    @Override
    @SuppressWarnings("unchecked")
    public void validate(T value) {
        final T zero = (T) value.getClass().cast(0);
        if (is(value).greaterThanOrEqualTo(zero))
            throw new IllegalArgumentException(String.format(POSITIVE_NUMBER_ERROR, value));
    }
}
