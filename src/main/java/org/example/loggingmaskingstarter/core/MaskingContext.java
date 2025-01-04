package org.example.loggingmaskingstarter.core;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MaskingContext {
    private String path; // Путь до значения, которое маскируется (например, "request.headers.Authorization")
    private String maskingType; // Тип маскирования (например, "STAR", "REGEX")

}
