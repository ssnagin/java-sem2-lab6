package com.ssnagin.collectionmanager.validation.factories;

import com.ssnagin.collectionmanager.validation.annotations.NotNull;
import com.ssnagin.collectionmanager.validation.validators.NotNullValidator;

import java.lang.annotation.Annotation;
import java.util.Set;

public class NotNullValidatorFactory<T> implements ValidatorFactory<T> {
    private static final Set<Class<? extends Annotation>> compatibleAnnotations = Set.of(NotNull.class);

    @Override
    public boolean isCompatibleWith(Class<? extends Annotation> annotationClass) {
        return compatibleAnnotations.contains(annotationClass);
    }

    @Override
    public NotNullValidator create(Annotation annotation) {
        if (annotation instanceof NotNull)
            return new NotNullValidator();

        else throw new IllegalArgumentException("Incompatible annotation");
    }
}