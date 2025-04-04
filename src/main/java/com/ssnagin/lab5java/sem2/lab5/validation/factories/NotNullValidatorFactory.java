package com.ssnagin.lab5java.sem2.lab5.validation.factories;

import com.ssnagin.lab5java.sem2.lab5.validation.annotations.NegativeNumber;
import com.ssnagin.lab5java.sem2.lab5.validation.annotations.NotEmpty;
import com.ssnagin.lab5java.sem2.lab5.validation.annotations.NotNull;
import com.ssnagin.lab5java.sem2.lab5.validation.validators.NotEmptyCharSequenceValidator;
import com.ssnagin.lab5java.sem2.lab5.validation.validators.NotNullValidator;

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