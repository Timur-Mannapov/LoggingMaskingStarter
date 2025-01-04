package org.example.loggingmaskingstarter.core;

import java.util.Map;

public class HttpLog {
    private String method;
    private String uriEndpoint;
    private int status;
    private Map<String, String> requestHeaders;
    private Map<String, String> responseHeaders;
    private long executionTime;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUriEndpoint() {
        return uriEndpoint;
    }

    public void setUriEndpoint(String uriEndpoint) {
        this.uriEndpoint = uriEndpoint;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Map<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

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
