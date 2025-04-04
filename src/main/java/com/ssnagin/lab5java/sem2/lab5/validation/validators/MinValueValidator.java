/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.validation.validators;

import lombok.AllArgsConstructor;
import static org.apache.commons.lang3.compare.ComparableUtils.is;

/**
 *
 * @author developer
 */
@AllArgsConstructor
public class MinValueValidator<T extends Comparable<T>> implements Validator<T> {
    private static final String MIN_VALUE_ERROR = "The value %s is lower than minimum %s";
    private T minimumValue;
    
    @Override
    public void validate(T value) {
        if(is(value).lessThan(minimumValue))
            throw new IllegalArgumentException(String.format(MIN_VALUE_ERROR, value, this.minimumValue));
    }
}

