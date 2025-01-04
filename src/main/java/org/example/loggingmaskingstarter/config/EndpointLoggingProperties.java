package org.example.loggingmaskingstarter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "endpoint.logging")
public record EndpointLoggingProperties(boolean active,
                                        boolean maskingEnabled,
                                        Map<String, MaskingConfig> maskingRules) {

    public record MaskingConfig(String maskingType){}
}
