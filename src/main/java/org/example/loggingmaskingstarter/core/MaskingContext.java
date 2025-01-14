package org.example.loggingmaskingstarter.core;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс, представляющий контекст для маскирования данных.
 * Содержит информацию о пути к данным и типе маскирования.
 */
@Getter
@AllArgsConstructor
public class MaskingContext {
    /**
     * Путь к данным, к которым применяется маскирование (например, "request.body", "response.header").
     */
    private String path;
    /**
     * Тип маскирования, которое необходимо применить (например, "RegexMasker", "StarterMasker").
     */
    private String maskingType;

}
