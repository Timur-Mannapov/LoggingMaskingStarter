package org.example.loggingmaskingstarter.core;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexMasker implements Masker {
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
