package com.ssnagin.collectionmanager.locales;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Locale;

public class LangLocalesAdapter {

    private static final ObjectProperty<Locale> currentLocaleProperty =
            new SimpleObjectProperty<>(LangLocales.getCurrentLocale());

    // Обертка для LangLocales.getMessage()
    public static StringBinding createBinding(String key, Object... args) {
        return new StringBinding() {
            {
                bind(currentLocaleProperty); // Привязка к изменению локали
            }
            @Override
            protected String computeValue() {
                return LangLocales.getMessage(key, args);
            }
        };
    }

    // Смена локали с уведомлением JavaFX
    public static void setLocale(Locale locale) {
        LangLocales.setLocale(locale);
        currentLocaleProperty.set(locale);
    }

}

// label.textProperty().bind(FxLocaleAdapter.createBinding("welcome.message"));