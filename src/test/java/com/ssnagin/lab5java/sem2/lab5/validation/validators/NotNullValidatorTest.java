package com.ssnagin.lab5java.sem2.lab5.validation.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class NotNullValidatorTest {

    @Test
    void validate() {
        NotNullValidator validator = new NotNullValidator();
        assertDoesNotThrow(() -> validator.validate(""));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(null));
    }
}