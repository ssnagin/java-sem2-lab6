/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ssnagin.collectionmanager.console;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Module that is responsible for parsing user's input
 * 
 * @author developer
 */
public class InputParser {
    
    public static ParsedString parse(Object text) {
        return InputParser.parse(text, null);
    }
    
    public static ParsedString parse(Object text, ParseMode parseMode) {
        
        ParsedString result;
        
        if (parseMode == null) parseMode = ParseMode.DEFAULT;

        if (text == null) return null;
        
        // Setting up our pattern searcher
        
        final Pattern pattern = Pattern.compile(parseMode.getRegex(), Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(text.toString());
        
        result = new ParsedString(text.toString());
        
        try {
            while (matcher.find()) {
//            System.out.println("Full match: " + matcher.group(0));
           
                for (int i = 1; i <= matcher.groupCount(); i++) {

                    if (matcher == null) continue;

                    result.addFromStream(matcher.group(i));
    //                System.out.println("Group " + i + ": " + matcher.group(i));
                }
        }
        } catch(Exception exception) {
            Console.error(exception.toString());
        }
        
        return result;
    }
}
