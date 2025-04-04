package com.ssnagin.lab5java.sem2.lab5.reflection.accessors;

import lombok.NonNull;

import java.lang.reflect.Field;

public interface ValueAccessor<T> {
    T accessValue(@NonNull Object object);

    interface Factory {
        <T> ValueAccessor<T> create(@NonNull Class<T> valueClass, @NonNull Field field);
    }
}

