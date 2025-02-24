package org.example.loggingmaskingstarter.logging;

import org.example.loggingmaskingstarter.core.LoggingInterceptor;
import org.example.loggingmaskingstarter.exception.StarterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Post-processor для проверки свойства 'endpoint.logging.active' в конфигурации.
 */
public class LoggingEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

    /**
     * Проверяет, что свойство `endpoint.logging.active` имеет допустимое значение (true или false).
     * Выбрасывает исключение, если значение некорректно.
     *
     * @param environment Окружение приложения.
     * @param application Объект SpringApplication.
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("Вызов LoggingEnvironmentPostProcessor");
        String enabledPropertyValue = environment.getProperty("endpoint.logging.active");

        if (enabledPropertyValue != null
                && !enabledPropertyValue.equalsIgnoreCase("true")
                && !enabledPropertyValue.equalsIgnoreCase("false")) {
            throw new StarterException("Ошибка во время проверки свойства 'endpoint.logging.active' в файле конфигурации. Допустимые значения: true или false.");
        }
    }
}
