package org.example.loggingmaskingstarter.core;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Реализация {@link Masker}, заменяющая каждый символ входной строки на '*'.
 */
@Component
public class StarterMasker implements Masker {
    /**
     * Заменяет каждый символ входной строки на '*'.
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
        return input.chars()
                .mapToObj(c -> "*")
                .collect(Collectors.joining());
    }
}
