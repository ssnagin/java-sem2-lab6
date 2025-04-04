package com.ssnagin.lab5java.sem2.lab5.reflection.accessors;

import lombok.NonNull;

import java.lang.reflect.Field;

public interface ValueAccessorManager {
    <T> T accessValue(@NonNull Class<T> valueClass, @NonNull Field field, @NonNull Object object);
}

