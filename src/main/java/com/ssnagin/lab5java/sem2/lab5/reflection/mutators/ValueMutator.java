package com.ssnagin.lab5java.sem2.lab5.reflection.mutators;

import lombok.NonNull;

import java.lang.reflect.Field;

public interface ValueMutator<T> {
    <P extends T> void mutateValue(@NonNull Object object, P value);

    interface Factory {
        <T> ValueMutator<T> create(@NonNull Class<T> valueClass, @NonNull Field field);
    }
}
