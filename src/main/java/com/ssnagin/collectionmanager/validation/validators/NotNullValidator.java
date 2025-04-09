package com.ssnagin.collectionmanager.validation.validators;

import java.util.Objects;

public class NotNullValidator implements Validator<Object> {
    public static final String NULL_VALUE_ERROR = "Expected value but null is given";

    @Override
    public void validate(Object value) {
        if(Objects.isNull(value))
            throw new IllegalArgumentException(NULL_VALUE_ERROR);
    }
}
