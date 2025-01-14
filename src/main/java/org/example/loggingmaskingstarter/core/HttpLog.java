package org.example.loggingmaskingstarter.core;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Класс для хранения информации о HTTP-запросе для логирования.
 */
@Getter
@Setter
public class HttpLog {
    /**
     * HTTP-метод (GET, POST, PUT и т.д.).
     */
    private String method;
    /**
     * URI эндпоинта.
     */
    private String uriEndpoint;
    /**
     * HTTP-статус ответа.
     */
    private int status;
    /**
     * Заголовки запроса.
     */
    private Map<String, String> requestHeaders;
    /**
     * Заголовки ответа.
     */
    private Map<String, String> responseHeaders;
    /**
     * Время выполнения запроса в миллисекундах.
     */
    private long executionTime;
    /**
     * Тело запроса.
     */
    private String requestBody;
    /**
     * Тело ответа.
     */
    private String responseBody;

    /**
     * Выводит сообщение в виде строки.
     * @return сообщение.
     */
    @Override
    public String toString() {
        return "\n=========================================\n" +
                "Тип запроса: " + method + "\n" +
                "URI эндпоинта: " + uriEndpoint + "\n" +
                "Статус: " + status + "\n" +
                "Заголовки запроса: " + requestHeaders + "\n" +
                "Заголовки ответа: " + responseHeaders + "\n" +
                "Время выполнения запроса: " + executionTime + " мс" + "\n" +
                "=========================================";
    }


}
