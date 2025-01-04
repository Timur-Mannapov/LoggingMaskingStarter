package org.example.loggingmaskingstarter.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@ConfigurationProperties(prefix = "endpoint.logging")
@Validated
public class EndpointLoggingProperties {

    private boolean active = false;
    private boolean maskingEnabled = false;

    @Setter
    private int requestBodySizeLimit = 1024;
    @Setter
    private int responseBodySizeLimit = 1024;


    private Map<String, MaskingConfig> maskingRules;

    public boolean active() {
        return active;
    }

    public EndpointLoggingProperties active(boolean active) {
        this.active = active;
        return this;
    }

    public boolean maskingEnabled() {
        return maskingEnabled;
    }

    public EndpointLoggingProperties maskingEnabled(boolean maskingEnabled) {
        this.maskingEnabled = maskingEnabled;
        return this;
    }

    public int requestBodySizeLimit() {
        return requestBodySizeLimit;
    }

    public int responseBodySizeLimit() {
        return responseBodySizeLimit;
    }

    public Map<String, MaskingConfig> maskingRules() {
        return maskingRules;
    }

    public EndpointLoggingProperties maskingRules(Map<String, MaskingConfig> maskingRules) {
        this.maskingRules = maskingRules;
        return this;
    }


    public static class MaskingConfig {
        private String maskingType;

        public String maskingType() {
            return maskingType;
        }
        public void setMaskingType(String maskingType) {
            this.maskingType = maskingType;
        }
    }

}
