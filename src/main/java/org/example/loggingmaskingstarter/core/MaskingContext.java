package org.example.loggingmaskingstarter.core;

public class MaskingContext {
    private String path; // Путь до значения, которое маскируется (например, "request.headers.Authorization")
    private String maskingType; // Тип маскирования (например, "STAR", "REGEX")

    // Геттеры и сеттеры
    public MaskingContext(String path, String maskingType) {
        this.path = path;
        this.maskingType = maskingType;
    }

    public String getPath() {
        return path;
    }

    public String getMaskingType() {
        return maskingType;
    }
}
