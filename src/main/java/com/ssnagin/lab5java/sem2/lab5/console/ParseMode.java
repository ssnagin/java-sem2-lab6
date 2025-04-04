/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ssnagin.lab5java.sem2.lab5.console;

/**
 *
 * @author developer
 */
public enum ParseMode {
    DEFAULT("(?:[/-]+(?<val1>\\S+?))|(?:\\\"[/-]+(?<val2>.+?)\\\")|(?:'[/-]+(?<val3>.+?)')|(?:\\\"(?<val4>.+?)\\\")|(?:'(?<val5>.+?)')|(?<val6>\\S+)"),
    COMMAND_ONLY("(?<script>^\\S+)|\\s*(?<singleArgument>.+\\S)"),
    UNKNOWN("");
    
    private final String regex;
    
    ParseMode(String regex) {
        this.regex = regex;
    }
    
    public String getRegex() {
        return this.regex;
    }
}
