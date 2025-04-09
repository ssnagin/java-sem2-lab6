package com.ssnagin.collectionmanager.reflection.accessors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InaccessibleObjectException;

import static org.junit.jupiter.api.Assertions.*;

class CombiningValueAccessorManagerTest {

    @Test
    void accessValue() {
        @AllArgsConstructor
        class WithPublicField {
            public int f;
        }

        @AllArgsConstructor
        class WithPrivateField {
            private int f;
        }

        @Getter
        @AllArgsConstructor
        class WithGetter {
            private int f;
        }

        ValueAccessorManager valueAccessorManager = CombiningValueAccessorManager.getDefaultInstance();
        assertDoesNotThrow(() -> {
            assertEquals(10, valueAccessorManager.accessValue(Integer.class, WithPublicField.class.getDeclaredField("f"), new WithPublicField(10)));
            assertEquals(10, valueAccessorManager.accessValue(Integer.class, WithPrivateField.class.getDeclaredField("f"), new WithPrivateField(10)));
            assertEquals(10, valueAccessorManager.accessValue(Integer.class, WithGetter.class.getDeclaredField("f"), new WithGetter(10)));
        });
        assertThrows(InaccessibleObjectException.class, () -> valueAccessorManager.accessValue(Integer.class, WithGetter.class.getDeclaredField("f"), new WithPublicField(10)));
    }
}