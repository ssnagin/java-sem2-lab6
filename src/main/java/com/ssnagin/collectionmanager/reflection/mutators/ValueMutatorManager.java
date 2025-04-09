package com.ssnagin.collectionmanager.reflection.mutators;

import lombok.NonNull;

import java.lang.reflect.Field;

public interface ValueMutatorManager {
    <T> void mutateValue(@NonNull Field field, @NonNull Object object, T value);
}
