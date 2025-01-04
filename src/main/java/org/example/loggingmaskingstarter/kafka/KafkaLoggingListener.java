package org.example.loggingmaskingstarter.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.loggingmaskingstarter.config.EndpointLoggingProperties;
import org.example.loggingmaskingstarter.core.Masker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class KafkaLoggingListener {
    private static final Logger log = LoggerFactory.getLogger(KafkaLoggingListener.class);
    private final EndpointLoggingProperties properties;
    private final ObjectMapper objectMapper;
    private final Map<String, Masker> maskers;


    public KafkaLoggingListener(EndpointLoggingProperties properties, ObjectMapper objectMapper, Map<String, Masker> maskers) {
        this.properties = properties;
        this.objectMapper = objectMapper;
        this.maskers = maskers;
    }

    @KafkaListner(topics = "test-topic")
    public void receive(@Payload String message) {
        if (Objects.isNull(message) || properties.maskingEnabled() == false) {
            log.info("Сообщение из Kafka: {}", message);
            return;
        }
        String maskedMessage = maskMessage(message);
        log.info("Сообщение из Kafka: {}", maskedMessage);

    }
    private String maskMessage(String message){
        if(Objects.isNull(properties.maskingRules())){
            return message;
        }
        String maskingType = Optional.ofNullable(properties.maskingRules().get("kafka.message")).map(EndpointLoggingProperties.MaskingConfig::maskingType).orElse("");
        return Optional.ofNullable(maskers.get(maskingType)).map(masker -> masker.mask(message, new MaskingContext("kafka.message", maskingType))).orElse(message);
    }
}
