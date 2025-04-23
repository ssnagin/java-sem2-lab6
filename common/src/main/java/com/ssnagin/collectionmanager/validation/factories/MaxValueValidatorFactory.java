package com.ssnagin.collectionmanager.validation.factories;

import com.ssnagin.collectionmanager.validation.annotations.MaxValue;
import com.ssnagin.collectionmanager.validation.validators.MaxValueValidator;

import java.lang.annotation.Annotation;
import java.util.Set;

public class MaxValueValidatorFactory<T extends Comparable<T>> implements ValidatorFactory<T> {
    private static final Set<Class<? extends Annotation>> compatibleAnnotations = Set.of(MaxValue.class);

    @Override
    public boolean isCompatibleWith(Class<? extends Annotation> annotationClass) {
        return compatibleAnnotations.contains(annotationClass);
    }

    @Override
    public MaxValueValidator<T> create(Annotation annotation) {
        if (annotation instanceof MaxValue maxValueAnnotation) {
            @SuppressWarnings("unchecked")
            T maxValue = (T) Long.valueOf(maxValueAnnotation.value());

            return new MaxValueValidator<>(maxValue);
        } else throw new IllegalArgumentException("Incompatible annotation");
    }
}