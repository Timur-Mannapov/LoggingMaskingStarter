package org.example.loggingmaskingstarter.core;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Реализация {@link Masker}, маскирующая данные с помощью регулярных выражений.
 * Заменяет все цифры, кроме последних четырёх, на '*'.
 */
@Component
public class RegexMasker implements Masker {
    /**
     * Маскирует входную строку, заменяя все цифры, кроме последних четырёх, на '*'.
     *
     * @param input   Входная строка для маскирования.
     * @param context Контекст маскирования, не используется в данной реализации.
     * @return Замаскированная строка.
     */
    @Override
    public String mask(String input, MaskingContext context) {
        if (Objects.isNull(input) || input.isEmpty()) {
            return input;
        }
        Pattern pattern = Pattern.compile("\\d(?=\\d{4})");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("*");
    }
}
