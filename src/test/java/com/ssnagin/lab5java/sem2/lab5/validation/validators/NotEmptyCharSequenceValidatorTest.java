package com.ssnagin.lab5java.sem2.lab5.validation.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotEmptyCharSequenceValidatorTest {

    @Test
    void validate() {
        NotEmptyCharSequenceValidator<String> validator = new NotEmptyCharSequenceValidator<>();
        assertDoesNotThrow(() -> validator.validate("not empty"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(""));
    }
}