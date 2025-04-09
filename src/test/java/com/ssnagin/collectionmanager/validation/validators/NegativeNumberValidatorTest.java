package com.ssnagin.collectionmanager.validation.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NegativeNumberValidatorTest {

    @Test
    void validate() {
        NegativeNumberValidator<Integer> validator = new NegativeNumberValidator<Integer>();
        assertDoesNotThrow(() -> validator.validate(-10));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(0));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(10));
    }
}