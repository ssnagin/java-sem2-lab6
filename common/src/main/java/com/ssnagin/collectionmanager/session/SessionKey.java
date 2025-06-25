package com.ssnagin.collectionmanager.session;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class SessionKey implements Serializable {
    private char[] sessionKey;

    @Override
    public String toString() {
        char[] chars = sessionKey;
        if (chars == null || chars.length == 0) {
            return "[Empty data]";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            // Заменяем непечатаемые символы на точки
            if (Character.isISOControl(c) || Character.isWhitespace(c) || c == '\u0000') {
                sb.append('.');
            } else {
                sb.append(c);
            }

            // Добавляем пробел каждые 8 символов для читаемости
            if ((i + 1) % 8 == 0) {
                sb.append(' ');
            }
        }

        return sb.toString();
    }
}
