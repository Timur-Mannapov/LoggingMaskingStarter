package org.example.loggingmaskingstarter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.loggingmaskingstarter.core.LoggingInterceptor;
import org.example.loggingmaskingstarter.core.Masker;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Автоконфигурация для включения логирования и маскирования.
 */
@AutoConfiguration
@EnableConfigurationProperties(EndpointLoggingProperties.class)
@ConditionalOnProperty(prefix = "endpoint.logging", value = "active", havingValue = "true")
public class LoggingAutoConfiguration {
    /**
     * Создает бин LoggingInterceptor
     * @param properties настройки
     * @param maskers мапа всех маскировщиков
     * @param objectMapper обьект для сериализации json
     * @return LoggingInterceptor
     */
    @Bean
    @ConditionalOnExpression("${endpoint.logging.active:false}")
    public LoggingInterceptor loggingInterceptor(EndpointLoggingProperties properties, Map<String, Masker> maskers, ObjectMapper objectMapper) {
        return new LoggingInterceptor(properties, maskers, objectMapper);
    }

    /**
     * Создает ObjectMapper
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    /**
     * Создает Map маскировщиков
     * @param maskers список всех маскировщиков
     * @return Map маскировщиков
     */
    @Bean
    public Map<String, Masker> maskers(List<Masker> maskers) {
        return maskers.stream()
                .collect(Collectors.toMap(masker -> masker.getClass().getSimpleName(), masker -> masker));
    }

    /**
     * Создает WebMvcConfigurer для добавления перехватчика
     * @param loggingInterceptor перехватчик
     * @return WebMvcConfigurer
     */
    @Bean
    @ConditionalOnBean(name = "loggingInterceptor")
    public WebMvcConfigurer webMvcConfigurer(LoggingInterceptor loggingInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(loggingInterceptor);
            }
        };
    }
}
