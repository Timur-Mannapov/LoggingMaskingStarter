package org.example.loggingmaskingstarter.core;

import java.util.Objects;
import java.util.stream.Collectors;

public class StarterMasker implements Masker {
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
