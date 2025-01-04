package org.example.loggingmaskingstarter.core;

public interface Masker {
    String mask(String input, MaskingContext context);
}