/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.console;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Main class that responsible for console interactions
 *
 * @author developer
 */
@EqualsAndHashCode
@ToString
public class Console {

    private static final String SHELL_ARROW = " @ _ @ > ";

    public static String getShellArrow() {
        return SHELL_ARROW;
    }

    public static void log(Object text) {
        Console.separatePrint(text.toString(), "  LOG  ");
    }

    public static void error(Object text) {
        Console.separatePrint(text, " ERROR ");
    }

    public static void error(Exception exception) {
        Console.separatePrint(exception.getMessage(), " ERROR ");
    }

    public static void separatePrint(Object text, String leftSide) {

        if (leftSide.length() > 7) {
            leftSide = leftSide.substring(0, 7);
        } else {
            for (int i = 0; i < 7 - leftSide.length(); i++) {
                leftSide += " ";
            }
        }

        String prepared = leftSide + "| " + text.toString();
        Console.println(prepared);
    }

    public static void print(Object text) {
        System.out.print(text.toString());
    }

    public static void println(Object text) {
        System.out.println(text.toString());
    }
}
