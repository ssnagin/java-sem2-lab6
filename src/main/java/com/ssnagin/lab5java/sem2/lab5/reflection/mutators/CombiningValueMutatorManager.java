package com.ssnagin.lab5java.sem2.lab5.reflection.mutators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.util.List;

@RequiredArgsConstructor
public class CombiningValueMutatorManager implements ValueMutatorManager{
    private final List<ValueMutator.Factory> valueMutatorFactories;

    ///  TODO: Constructors Here

    @Override
    public <T> void mutateValue(@NonNull Field field, @NonNull Object object, T value) {

    }

    /// TODO: Singleton handler & access here
}
