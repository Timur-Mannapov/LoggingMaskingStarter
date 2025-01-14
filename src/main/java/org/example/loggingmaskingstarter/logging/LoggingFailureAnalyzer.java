package org.example.loggingmaskingstarter.logging;

import org.example.loggingmaskingstarter.exception.StarterException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * Класс для анализа и предоставления сообщения об ошибке в случае исключения StarterException
 */
public class LoggingFailureAnalyzer extends AbstractFailureAnalyzer<StarterException> {
    /**
     * Метод для анализа исключения.
     *
     * @param rootFailure Throwable
     * @param cause исключение StarterException
     * @return FailureAnalysis
     */
    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, StarterException cause) {
        return new FailureAnalysis(cause.getMessage(),
                "Укажите верные значения для свойства в файле конфигурации. Допустимые значения: true или false.",
                cause);
    }
}
