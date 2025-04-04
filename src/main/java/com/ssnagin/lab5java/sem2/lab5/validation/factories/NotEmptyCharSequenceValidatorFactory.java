package com.ssnagin.lab5java.sem2.lab5.validation.factories;

import com.ssnagin.lab5java.sem2.lab5.validation.annotations.NegativeNumber;
import com.ssnagin.lab5java.sem2.lab5.validation.annotations.NotEmpty;
import com.ssnagin.lab5java.sem2.lab5.validation.validators.NegativeNumberValidator;
import com.ssnagin.lab5java.sem2.lab5.validation.validators.NotEmptyCharSequenceValidator;

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