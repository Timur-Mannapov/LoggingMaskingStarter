package org.example.loggingmaskingstarter.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

/**
 * Класс для хранения настроек логирования и маскирования.
 */
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

    /**
     * Возвращает активность логирования
     * @return boolean
     */
    public boolean active() {
        return active;
    }
    /**
     * Устанавливает активность логирования.
     * @param active активность.
     * @return EndpointLoggingProperties
     */
    public EndpointLoggingProperties active(boolean active) {
        this.active = active;
        return this;
    }

    /**
     * Возвращает статус маскировки.
     * @return boolean
     */
    public boolean maskingEnabled() {
        return maskingEnabled;
    }

    /**
     * Устанавливает статус маскировки
     * @param maskingEnabled статус.
     * @return EndpointLoggingProperties
     */
    public EndpointLoggingProperties maskingEnabled(boolean maskingEnabled) {
        this.maskingEnabled = maskingEnabled;
        return this;
    }

    /**
     * Возвращает лимит тела запроса.
     * @return int
     */
    public int requestBodySizeLimit() {
        return requestBodySizeLimit;
    }

    /**
     * Возвращает лимит тела ответа.
     * @return int
     */
    public int responseBodySizeLimit() {
        return responseBodySizeLimit;
    }

    /**
     * Возвращает мапу правил маскировки.
     * @return Map
     */
    public Map<String, MaskingConfig> maskingRules() {
        return maskingRules;
    }

    /**
     * Устанавливает мапу правил маскировки.
     * @param maskingRules Map правил маскировки.
     * @return EndpointLoggingProperties
     */
    public EndpointLoggingProperties maskingRules(Map<String, MaskingConfig> maskingRules) {
        this.maskingRules = maskingRules;
        return this;
    }

    /**
     * Вложенный класс для представления настроек маскировки.
     */
    public static class MaskingConfig {
        private String maskingType;

        /**
         * Возвращает тип маскировки.
         * @return String
         */
        public String maskingType() {
            return maskingType;
        }
        /**
         * Устанавливает тип маскировки.
         * @param maskingType тип маскировки.
         */
        public void setMaskingType(String maskingType) {
            this.maskingType = maskingType;
        }
    }

}
