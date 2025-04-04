package com.ssnagin.lab5java.sem2.lab5.validation.validators;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MaxValueValidatorTest {
    @Test
    public void testValidate() {
        MaxValueValidator<Integer> validator = new MaxValueValidator<>(100);
        assertDoesNotThrow(() -> validator.validate(50));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(150));
    }  
}
