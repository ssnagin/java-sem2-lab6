package com.ssnagin.lab5java.sem2.lab5.reflection.mutators;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SetterValueMutator<T> implements ValueMutator<T> {
    private static final String SETTER_NAME_PATTERN = "set%s";
    private final String setterName;

    public SetterValueMutator(@NonNull Class<T> valueClass, @NonNull Field field) {
        assert ClassUtils.isAssignable(valueClass, field.getType());
        this.setterName = field.getDeclaringClass().isRecord() ? field.getName() : String.format(SETTER_NAME_PATTERN, StringUtils.capitalize(field.getName()));
    }

    @Override
    @SneakyThrows
    public <P extends T> void mutateValue(@NonNull Object object, P value) {
        Method valueSetter = object.getClass().getMethod(setterName, value.getClass());
        valueSetter.invoke(object, value);
    }

    public static class Factory implements ValueMutator.Factory {
        @Override
        public <T> ValueMutator<T> create(@NonNull Class<T> valueClass, @NonNull Field field) {
            return new SetterValueMutator<>(valueClass, field);
        }
    }
}
