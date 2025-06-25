package com.ssnagin.collectionmanager.locales;

import lombok.Getter;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LangLocales {

    private static ResourceBundle bundle;
    @Getter
    private static Locale currentLocale = Locale.getDefault();

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("com.ssnagin.collectionmanager.locales.lc", locale);
    }

    public static String getMessage(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "[" + key + "]"; // Заглушка, если ключ не найден
        }
    }

    public static String getMessage(String key, Object... args) {
        try {
            String pattern = getMessage(key);
            return args.length > 0 ? String.format(pattern, args) : pattern;
        } catch (Exception e) {
            return "[" + key + "]"; // Заглушка при ошибке форматирования
        }
    }

}

/*

String welcome = LangLocales.getMessage("welcome.message");
System.out.println(welcome);

String userWelcome = LangLocales.getMessage("user.welcome", "Alice");
System.out.println(userWelcome); // "Welcome, Alice!"

LangLocales.setLocale(new Locale("ru"));
String welcomeInRussian = LangLocales.getMessage("welcome.message");
System.out.println(welcomeInRussian); // "Привет!"



 */