package com.ssnagin.collectionmanager.validation;

import com.ssnagin.collectionmanager.reflection.accessors.CombiningValueAccessorManager;
import com.ssnagin.collectionmanager.validation.factories.ValidatorFactory;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ValidationManager {

    @Getter
    private static final ValidationManager instance = new ValidationManager();

    private final List<ValidatorFactory> validatorFactories = new ArrayList<>();

    public void validate(Object object) {
        // Посмотреть все declaredFields
        // getterAccessor
        // fieldAccessor

        if (object == null) throw new IllegalArgumentException("Object cannot be null in validation");

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            validateField(object, field);
        }
    }

    public void register(ValidatorFactory validatorFactory) {
        this.validatorFactories.add(validatorFactory);
    }

    public void validateField(Object object, Field field) {
        Annotation[] annotations = field.getAnnotations();

        for (Annotation annotation : annotations) {
            ValidatorFactory<?> validatorFactory = findCompatibleFactory(annotation.annotationType());

            if (validatorFactory != null) {
                CombiningValueAccessorManager.getDefaultInstance().accessValue(field.getType(), field, object);
            }
        }
    }

    private ValidatorFactory<?> findCompatibleFactory(Class<? extends Annotation> annotationType) {
        for (ValidatorFactory<?> factory : validatorFactories) {
            if (factory.isCompatibleWith(annotationType)) {
                return factory;
            }
        }
        return null;
    }
}
