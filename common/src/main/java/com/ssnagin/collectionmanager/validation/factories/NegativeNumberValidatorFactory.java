package com.ssnagin.collectionmanager.validation.factories;

import com.ssnagin.collectionmanager.validation.annotations.NegativeNumber;
import com.ssnagin.collectionmanager.validation.validators.NegativeNumberValidator;

import java.lang.annotation.Annotation;
import java.util.Set;

public class NegativeNumberValidatorFactory<T extends Number & Comparable<T>> implements ValidatorFactory<T> {
    private static final Set<Class<? extends Annotation>> compatibleAnnotations = Set.of(NegativeNumber.class);

    @Override
    public boolean isCompatibleWith(Class<? extends Annotation> annotationClass) {
        return compatibleAnnotations.contains(annotationClass);
    }

    @Override
    public NegativeNumberValidator<T> create(Annotation annotation) {
        if (annotation instanceof NegativeNumber)
            return new NegativeNumberValidator<>();

        else throw new IllegalArgumentException("Incompatible annotation");
    }
}