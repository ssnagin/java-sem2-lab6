package com.ssnagin.collectionmanager.reflection.accessors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class CombiningValueAccessorManager implements ValueAccessorManager {
    private final List<ValueAccessor.Factory> valueAccessorFactories;

    public CombiningValueAccessorManager(@NonNull ValueAccessor.Factory... valueAccessorFactories) {
        this(Arrays.asList(valueAccessorFactories));
    }

    public CombiningValueAccessorManager() {
        this(new GetterValueAccessor.Factory(), new FieldValueAccessor.Factory());
    }

    public <T> T accessValue(@NonNull Class<T> valueClass, @NonNull Field field, @NonNull Object object) {
        final List<Exception> deferredExceptions = new ArrayList<>();
        final List<ValueAccessor<T>> valueAccessors = valueAccessorFactories.stream().map(factory -> factory.create(valueClass, field)).toList();
        for (ValueAccessor<T> valueAccessor : valueAccessors) {
            try {
                return valueClass.cast(valueAccessor.accessValue(object)); // Здесь всё ломается
            } catch (Exception exception) {
                deferredExceptions.add(exception);
            }
        }
        throw new InaccessibleObjectException("Tried to access value " + valueClass.getName() + " from class " + object.getClass().getName() + " but all accessors have failed:\n" + String.join("\n", deferredExceptions.stream().map(Exception::toString).toList()));
    }


    public static CombiningValueAccessorManager getDefaultInstance() {
        return CombiningValueAccessorManagerHolder.INSTANCE;
    }

    private static class CombiningValueAccessorManagerHolder {
        private static final CombiningValueAccessorManager INSTANCE = new CombiningValueAccessorManager();
    }
}
