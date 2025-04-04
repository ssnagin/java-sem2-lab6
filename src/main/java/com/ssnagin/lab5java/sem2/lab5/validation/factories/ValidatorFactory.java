package com.ssnagin.lab5java.sem2.lab5.validation.factories;

import com.ssnagin.lab5java.sem2.lab5.validation.validators.Validator;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface ValidatorFactory<T> {
    boolean isCompatibleWith(Class<? extends Annotation> annotationClass);
    <A extends Annotation> Validator<T> create(A annotation);
}
