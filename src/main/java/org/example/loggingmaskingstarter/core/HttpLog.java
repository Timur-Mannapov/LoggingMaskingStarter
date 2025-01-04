package org.example.loggingmaskingstarter.core;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class HttpLog {
    private String method;
    private String uriEndpoint;
    private int status;
    private Map<String, String> requestHeaders;
    private Map<String, String> responseHeaders;
    private long executionTime;
    private String requestBody;
    private String responseBody;


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
