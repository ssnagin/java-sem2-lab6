package com.ssnagin.lab5java.sem2.lab5.validation.factories;

import com.ssnagin.lab5java.sem2.lab5.validation.annotations.MinValue;
import com.ssnagin.lab5java.sem2.lab5.validation.validators.MinValueValidator;

import java.lang.annotation.Annotation;
import java.util.Set;

public class MinValueValidatorFactory<T extends Comparable<T>> implements ValidatorFactory<T> {
    private static final Set<Class<? extends Annotation>> compatibleAnnotations = Set.of(MinValue.class);

    @Override
    public boolean isCompatibleWith(Class<? extends Annotation> annotationClass) {
        return compatibleAnnotations.contains(annotationClass);
    }

    @Override
    public MinValueValidator<T> create(Annotation annotation) {
        if (annotation instanceof MinValue minValueAnnotation) {
            @SuppressWarnings("unchecked")
            T minValue = (T) Long.valueOf(minValueAnnotation.value());

            return new MinValueValidator<>(minValue);
        }

        else throw new IllegalArgumentException("Incompatible annotation");
    }
}