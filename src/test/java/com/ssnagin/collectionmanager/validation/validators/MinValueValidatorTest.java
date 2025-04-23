package com.ssnagin.collectionmanager.validation.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MinValueValidatorTest {
    @Test
    public void testValidate() {
        MinValueValidator<Integer> validator = new MinValueValidator<>(0);
        assertDoesNotThrow(() -> validator.validate(100));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(-100));
    }
}
