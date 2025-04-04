/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.commands.interfaces;

/**
 *
 * @author developer
 */
public interface Manageable<T> {
    void register(T object);
    T get(String string);
}
