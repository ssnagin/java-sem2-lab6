package com.ssnagin.lab5java.sem2.lab5.validation.factories;

import com.ssnagin.lab5java.sem2.lab5.validation.annotations.NegativeNumber;
import com.ssnagin.lab5java.sem2.lab5.validation.annotations.PositiveNumber;
import com.ssnagin.lab5java.sem2.lab5.validation.validators.NegativeNumberValidator;

import java.lang.annotation.Annotation;
import java.util.Set;

public class PositiveNumberValidatorFactory<T extends Number & Comparable<T>> implements ValidatorFactory<T> {
    private static final Set<Class<? extends Annotation>> compatibleAnnotations = Set.of(PositiveNumber.class);

    @Override
    public boolean isCompatibleWith(Class<? extends Annotation> annotationClass) {
        return compatibleAnnotations.contains(annotationClass);
    }

    @Override
    public NegativeNumberValidator<T> create(Annotation annotation) {
        if (annotation instanceof PositiveNumber)
            return new NegativeNumberValidator<>();

        else throw new IllegalArgumentException("Incompatible annotation");
    }
}