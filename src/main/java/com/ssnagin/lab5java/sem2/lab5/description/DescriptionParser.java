/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.description;

import com.ssnagin.lab5java.sem2.lab5.description.annotations.Description;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author developer
 */
public class DescriptionParser {
    
    public static String getRecursedDescription(Class<?> clazz, HashMap<Integer, Class<?>> classHistory) {
        return getRecursedDescription(clazz, classHistory, 10);
    }
    
    public static String getRecursedDescription(Class<?> clazz, HashMap<Integer, Class<?>> classHistory, int recursionLimit) {
        
        if (recursionLimit <= 0) return "";
        
        for (Map.Entry<Integer, Class<?>> entry : classHistory.entrySet()) {
            // If current clazz matches the previous in history, increase:
            if (entry.getValue().equals(clazz)) {
                classHistory.put(entry.getKey(), clazz);
            }
        }
        
        classHistory.put(classHistory.size(), clazz);
            
//        if (classHistory.contains(clazz)) return "";
//        classHistory.add(clazz);        
        
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        
        String offset = buildOffset(classHistory.size());

        stringBuilder
                .append("\n")
                .append(offset)
                .append(clazz.getSimpleName());
        
        for (Field field : fields) {
            
            // If no annotation presented, skip
            if (!field.isAnnotationPresent(Description.class)) continue;
            
            // If field is Enum, we'll have another deal:
            if (field.getType().isEnum()) {

                Object[] enumValues = field.getType().getEnumConstants();
                // Добавляем информацию о значениях enum в результат
                stringBuilder
                        .append("\n")
                        .append(offset)
                        .append(" ")
                        .append(field.getType().getSimpleName())
                        .append("\n")
                        .append(offset)
                        .append(" ")
                        .append("- Возможные значения: ");

                for (Object enumValue : enumValues) {
                    stringBuilder.append(enumValue).append(" ");
                }
                
                continue;
            }
            
            // Dangerous zone
            if (!isPrimitive(field.getType())) {
                stringBuilder.append(getRecursedDescription(field.getType(), classHistory, recursionLimit - 1));
                continue;
            }
            
            Description description = field.getAnnotation(Description.class);
            
            stringBuilder
                    .append("\n")
                    .append(offset)
                    .append("- ")
                    .append(description.name())
                    .append(" | ")
                    .append(description.description());
        }
        
        return stringBuilder.toString();
    }
    
    private static String buildOffset(int offset) {
        
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < offset; i++) {
            result.append(" ");
        }

        return result.toString();
    }
    
    private static boolean isPrimitive(Class<?> clazz) {
    return  clazz.isEnum() ||
            clazz.isPrimitive() || 
           clazz == String.class || 
           Number.class.isAssignableFrom(clazz) || 
           Boolean.class.isAssignableFrom(clazz);
    }
}
