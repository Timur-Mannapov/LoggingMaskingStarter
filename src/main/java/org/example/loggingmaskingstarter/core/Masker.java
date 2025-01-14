package org.example.loggingmaskingstarter.core;

/**
 * Интерфейс для реализации маскировщиков данных.
 */
public interface Masker {
    /**
     * Маскирует входную строку, используя заданный контекст.
     *
     * @param input   Входная строка для маскирования.
     * @param context Контекст маскирования, содержащий информацию о типе данных и пути.
     * @return Замаскированная строка.
     */
    String mask(String input, MaskingContext context);
}