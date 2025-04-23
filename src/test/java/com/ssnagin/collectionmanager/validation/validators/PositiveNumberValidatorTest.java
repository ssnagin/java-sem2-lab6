package com.ssnagin.collectionmanager.validation.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositiveNumberValidatorTest {
    @Test
    void validate() {
        PositiveNumberValidator<Integer> validator = new PositiveNumberValidator<Integer>();
        assertThrows(IllegalArgumentException.class, () -> validator.validate(-10));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(0));
        assertDoesNotThrow(() -> validator.validate(10));
    }
}