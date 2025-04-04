package com.ssnagin.lab5java.sem2.lab5.validation.factories;

import com.ssnagin.lab5java.sem2.lab5.validation.annotations.MaxValue;
import com.ssnagin.lab5java.sem2.lab5.validation.annotations.MinValue;
import com.ssnagin.lab5java.sem2.lab5.validation.validators.MaxValueValidator;
import com.ssnagin.lab5java.sem2.lab5.validation.validators.MinValueValidator;

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
        }

        else throw new IllegalArgumentException("Incompatible annotation");
    }
}