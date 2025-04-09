package com.ssnagin.collectionmanager.validation.factories;

import com.ssnagin.collectionmanager.validation.annotations.NotEmpty;
import com.ssnagin.collectionmanager.validation.validators.NotEmptyCharSequenceValidator;

import java.lang.annotation.Annotation;
import java.util.Set;

public class NotEmptyCharSequenceValidatorFactory<T extends CharSequence> implements ValidatorFactory<T> {
    private static final Set<Class<? extends Annotation>> compatibleAnnotations = Set.of(NotEmpty.class);

    @Override
    public boolean isCompatibleWith(Class<? extends Annotation> annotationClass) {
        return compatibleAnnotations.contains(annotationClass);
    }

    @Override
    public NotEmptyCharSequenceValidator<T> create(Annotation annotation) {
        if (annotation instanceof NotEmpty)
            return new NotEmptyCharSequenceValidator<>();

        else throw new IllegalArgumentException("Incompatible annotation");
    }
}