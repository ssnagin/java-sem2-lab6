package com.ssnagin.lab5java.sem2.lab5.reflection.accessors;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class GetterValueAccessor<T> implements ValueAccessor<T> {
    public static final String GETTER_NAME_PATTERN = "get%s";
    private static final String INCOMPATIBLE_GETTER_TYPE_ASSERTION_ERROR = "Accessor's and getter's types mismatch: %s is not %s";
    private final Class<T> valueClass;
    private final String getterName;

    @SneakyThrows
    public GetterValueAccessor(@NonNull Class<T> valueClass, @NonNull Field field) {
        this.valueClass = valueClass;
        this.getterName = field.getDeclaringClass().isRecord() ? field.getName() : String.format(GETTER_NAME_PATTERN, StringUtils.capitalize(field.getName()));
    }

    @Override
    @SneakyThrows
    public T accessValue(@NonNull Object object) {
        Method valueGetter = object.getClass().getMethod(getterName);
        assert ClassUtils.isAssignable(valueGetter.getReturnType(), valueClass) : String.format(INCOMPATIBLE_GETTER_TYPE_ASSERTION_ERROR, valueClass, valueGetter.getReturnType());
        return valueClass.cast(valueGetter.invoke(object));
    }

    public static class Factory implements ValueAccessor.Factory {
        @Override
        public <T> ValueAccessor<T> create(@NonNull Class<T> valueClass, @NonNull Field field) {
            return new GetterValueAccessor<>(valueClass, field);
        }
    }
}
