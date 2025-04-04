/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5;

/**
 *
 * @author developer
 */
public enum ApplicationStatus {
    RUNNING(-1),
    EXIT(0),
    ERROR(1);
    
    private final int code;
    
    ApplicationStatus(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return this.code;
    }
}
