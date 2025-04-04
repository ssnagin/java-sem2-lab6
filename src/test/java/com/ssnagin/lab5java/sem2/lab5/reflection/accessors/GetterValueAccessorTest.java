package com.ssnagin.lab5java.sem2.lab5.reflection.accessors;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GetterValueAccessorTest {
    @Test
    void accessValueOnRecord() {
        record RecordWithGetter(String s) {
        }

        final String expectedValue = "VALID";
        RecordWithGetter recordWithGetter = new RecordWithGetter(expectedValue);
        assertDoesNotThrow(() -> {
            ValueAccessor<String> accessor = new GetterValueAccessor<>(String.class, RecordWithGetter.class.getDeclaredField("s"));
            String accessedValue = accessor.accessValue(recordWithGetter);
            assertEquals(expectedValue, accessedValue);
        });

    }

    @Test
    void accessValueOnClass() {
        @Data
        @AllArgsConstructor
        class ClassWithGetter {
            private String s;
        }

        final String expectedValue = "VALID";
        ClassWithGetter recordWithGetter = new ClassWithGetter(expectedValue);
        assertDoesNotThrow(() -> {
            ValueAccessor<String> accessor = new GetterValueAccessor<>(String.class, ClassWithGetter.class.getDeclaredField("s"));
            String accessedValue = accessor.accessValue(recordWithGetter);
            assertEquals(expectedValue, accessedValue);
        });
    }
}