package com.ssnagin.collectionmanager.reflection.mutators;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;

public class FieldValueMutator<T> implements ValueMutator<T> {
    private final Field field;

    public FieldValueMutator(@NonNull Class<T> valueClass, @NonNull Field field) {
        assert ClassUtils.isAssignable(valueClass, field.getType());
        this.field = field;
    }

    @Override
    @SneakyThrows
    public <P extends T> void mutateValue(@NonNull Object object, P value) {
        assert ClassUtils.isAssignable(value.getClass(), field.getType());
        if (!field.trySetAccessible()) throw new IllegalAccessException(field.getName());
        field.set(object, value);
    }

    public static class Factory implements ValueMutator.Factory {
        @Override
        public <T> ValueMutator<T> create(@NonNull Class<T> valueClass, @NonNull Field field) {
            return new FieldValueMutator<>(valueClass, field);
        }
    }
}
