package org.example.loggingmaskingstarter.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.loggingmaskingstarter.config.EndpointLoggingProperties;
import org.example.loggingmaskingstarter.core.Masker;
import org.example.loggingmaskingstarter.core.MaskingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Слушатель Kafka, который логирует и маскирует сообщения.
 */
@Component
public class KafkaLoggingListener {
    private static final Logger log = LoggerFactory.getLogger(KafkaLoggingListener.class);
    private final EndpointLoggingProperties properties;
    private final ObjectMapper objectMapper;
    private final Map<String, Masker> maskers;

    /**
     * Конструктор {@link KafkaLoggingListener}.
     *
     * @param properties  Настройки логирования и маскирования.
     * @param objectMapper  Объект для сериализации и десериализации JSON.
     * @param maskers Мапа всех доступных маскировщиков.
     */
    public KafkaLoggingListener(EndpointLoggingProperties properties, ObjectMapper objectMapper, Map<String, Masker> maskers) {
        this.properties = properties;
        this.objectMapper = objectMapper;
        this.maskers = maskers;
    }
    /**
     * Обрабатывает сообщения из Kafka.
     *
     * @param message Сообщение из Kafka.
     */
    @KafkaListener(topics = "test-topic")
    public void receive(@Payload String message) {
        if (Objects.isNull(message) || !properties.maskingEnabled()) {
            log.info("Сообщение из Kafka: {}", message);
            return;
        }
        String maskedMessage = maskMessage(message);
        log.info("Сообщение из Kafka: {}", maskedMessage);

    }

    /**
     * Маскирует сообщение Kafka, если это необходимо.
     *
     * @param message Сообщение Kafka.
     * @return Замаскированное сообщение.
     */
    private String maskMessage(String message){
        if(Objects.isNull(properties.maskingRules())){
            return message;
        }
        String maskingType = Optional.ofNullable(properties.maskingRules().get("kafka.message")).map(EndpointLoggingProperties.MaskingConfig::maskingType).orElse("");
        return Optional.ofNullable(maskers.get(maskingType)).map(masker -> masker.mask(message, new MaskingContext("kafka.message", maskingType))).orElse(message);
    }
}
