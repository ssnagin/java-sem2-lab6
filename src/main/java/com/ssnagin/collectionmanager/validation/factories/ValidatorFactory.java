package com.ssnagin.collectionmanager.validation.factories;

import com.ssnagin.collectionmanager.validation.validators.Validator;

import java.lang.annotation.Annotation;

public interface ValidatorFactory<T> {
    boolean isCompatibleWith(Class<? extends Annotation> annotationClass);
    <A extends Annotation> Validator<T> create(A annotation);
}
