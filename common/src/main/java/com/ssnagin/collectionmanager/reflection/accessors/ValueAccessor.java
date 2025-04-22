package com.ssnagin.collectionmanager.reflection.accessors;

import lombok.NonNull;

import java.lang.reflect.Field;

public interface ValueAccessor<T> {
    T accessValue(@NonNull Object object);

    interface Factory {
        <T> ValueAccessor<T> create(@NonNull Class<T> valueClass, @NonNull Field field);
    }
}

