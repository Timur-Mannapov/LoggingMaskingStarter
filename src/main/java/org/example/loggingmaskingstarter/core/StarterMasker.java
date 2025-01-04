package org.example.loggingmaskingstarter.core;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
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
