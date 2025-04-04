/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.collection.model;

import com.ssnagin.lab5java.sem2.lab5.collection.interfaces.Describable;
import com.ssnagin.lab5java.sem2.lab5.collection.interfaces.Randomize;
import com.ssnagin.lab5java.sem2.lab5.console.Console;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;

/**
 *
 * @author developer
 */
@EqualsAndHashCode
public abstract class Entity<T> implements Describable, Comparable<T>, Randomize<T> {
    
    @Override
    public abstract int compareTo(T object);
    
    /**
     * Shows description according to provided info in @Description
     * @return String
     */
    @Override
    public String getDescription() {
        return getDescription(1);
    }
    
    /**
     * Shows description according to provided info in @Description
     * 
     * @param depth
     * @return String
     */
    public String getDescription(int depth) {
        
        List<Field> fields = getAllFields(this.getClass());
        
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("Entity of ").append(
                this.getClass().getSimpleName()).append(":\n");
        
        for (Field field : fields) {
            String fieldName = field.getName();
            String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            if (Entity.class.isAssignableFrom(field.getType())) {
                try {
                    
                    Method getter = this.getClass().getMethod(getterName);
                    
                    if (!isGetter(getter)) continue;

                    Entity value = (Entity) getter.invoke(this);

                    stringBuilder.append(getIndent(depth)).append(fieldName).append(": ").append(value.getDescription(depth + 1)).append("\n");

                    continue;
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                    Console.error(ex.getMessage());
                }
            }
            
            try {
                
                Method getter = this.getClass().getMethod(getterName);
                
                if (isGetter(getter)) {

                    Object value = getter.invoke(this);
                    
                    stringBuilder.append(getIndent(depth)).append(fieldName).append(": ").append(value).append("\n");
                }
                
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException ex) {
                Console.error(ex.toString());
            }
           
        }
        
        return stringBuilder.toString();
    }
    
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                fields.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        
        return fields;
    }
    
    private static String getIndent(int indent) {
        String result = "";
        
        for (int i = 0; i < indent - 1; i++) {
            result += " ";
        }
        return result;
    }
    
    private static boolean isGetter(Method method) {
        
        return (method.getName().startsWith("get") && 
                method.getParameterCount() == 0 &&
                !method.getReturnType().equals(void.class));
    }
    
    private static boolean isSetter(Method method) {
        return (method.getName().startsWith("set") &&
                method.getParameterCount() == 0 &&
                method.getReturnType().equals(void.class));
    }
}
