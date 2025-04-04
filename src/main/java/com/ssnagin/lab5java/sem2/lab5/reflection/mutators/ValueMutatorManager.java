package com.ssnagin.lab5java.sem2.lab5.reflection.mutators;

import lombok.NonNull;

import java.lang.reflect.Field;

public interface ValueMutatorManager {
    <T> void mutateValue(@NonNull Field field, @NonNull Object object, T value);
}
