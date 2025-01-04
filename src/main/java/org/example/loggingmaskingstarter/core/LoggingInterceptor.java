package org.example.loggingmaskingstarter.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.loggingmaskingstarter.config.EndpointLoggingProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);
    private static final String START_TIME_ATTRIBUTE = "startTime";

    private final EndpointLoggingProperties properties;
    private final Map<String, Masker> maskers;

    private final ObjectMapper objectMapper;

    public LoggingInterceptor(EndpointLoggingProperties properties, Map<String, Masker> maskers, ObjectMapper objectMapper) {
        this.properties = properties;
        this.maskers = maskers;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME_ATTRIBUTE, start);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long start = (long) request.getAttribute(START_TIME_ATTRIBUTE);
        long end = System.currentTimeMillis();
        long duration = end - start;

        String method = request.getMethod();
        String requestURL = request.getRequestURL().toString();
        int statusCode = response.getStatus();

        Map<String, String> requestHeaders = getHeader(request);
        Map<String, String> responseHeaders = getHeader(response);

        String requestBody = getRequestBody(request);
        String responseBody = getResponseBody(response);


        if(properties.maskingEnabled()){
            requestHeaders = maskHeaders(requestHeaders, "request.headers");
            responseHeaders = maskHeaders(responseHeaders, "response.headers");
            requestBody = maskBody(requestBody, "request.body");
            responseBody = maskBody(responseBody, "response.body");
        }
        HttpLog httpLog = createMessage(method, requestURL, statusCode, duration, requestHeaders, responseHeaders, requestBody, responseBody);

        log.info("Лог HTTP {}", httpLog);
    }

    private String maskBody(String body, String path) {
        if (Objects.isNull(body) || Objects.isNull(properties.maskingRules())) {
            return body;
        }
        String maskingType = Optional.ofNullable(properties.maskingRules().get(path)).map(EndpointLoggingProperties.MaskingConfig::maskingType).orElse("");
        return Optional.ofNullable(maskers.get(maskingType))
                .map(masker -> masker.mask(body, new MaskingContext(path, maskingType)))
                .orElse(body);
    }


    private Map<String, String> maskHeaders(Map<String, String> headers, String headerType) {
        if(Objects.isNull(properties.maskingRules()) || headers.isEmpty()){
            return headers;
        }
        Map<String, String> maskedHeaders = new HashMap<>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String path = headerType + "." + key;
            if(properties.maskingRules().containsKey(path)){
                EndpointLoggingProperties.MaskingConfig maskingConfig = properties.maskingRules().get(path);
                String maskedValue = Optional.ofNullable(maskers.get(maskingConfig.maskingType()))
                        .map(masker -> masker.mask(entry.getValue(), new MaskingContext(path, maskingConfig.maskingType())))
                        .orElse(entry.getValue());
                maskedHeaders.put(key,maskedValue);
            } else{
                maskedHeaders.put(key, entry.getValue());
            }
        }
        return maskedHeaders;
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        if(request.getContentType() == null || !request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)){
            return "";
        }
        int maxBodySize = properties.requestBodySizeLimit();
        return limitBody(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), maxBodySize);
    }


    private String getResponseBody(HttpServletResponse response) throws IOException {
        if(response.getContentType() == null || !response.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)){
            return "";
        }

        int maxBodySize = properties.responseBodySizeLimit();
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        byte[] bodyBytes = responseWrapper.getContentAsByteArray();
        responseWrapper.copyBodyToResponse();
        return limitBody(new String(bodyBytes, StandardCharsets.UTF_8), maxBodySize);
    }

    private String limitBody(String body, int maxBodySize) {
        if (body.length() > maxBodySize) {
            return body.substring(0, maxBodySize) + "... (truncated)";
        }
        return body;
    }


    public Map<String, String> getHeader(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            headers.put(key, value);
        }
        return headers;
    }

    public Map<String, String> getHeader(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        for (String header : response.getHeaderNames()) {
            String value = response.getHeader(header);
            headers.put(header, value);
        }
        return headers;
    }

    private HttpLog createMessage(String method, String requestURL, int statusCode, long duration,
                                  Map<String, String> requestHeaders,
                                  Map<String, String> responseHeaders, String requestBody, String responseBody) {
        HttpLog httpLog = new HttpLog();
        httpLog.setMethod(method);
        httpLog.setUriEndpoint(requestURL);
        httpLog.setStatus(statusCode);
        httpLog.setRequestHeaders(requestHeaders);
        httpLog.setResponseHeaders(responseHeaders);
        httpLog.setExecutionTime(duration);
        httpLog.setRequestBody(requestBody);
        httpLog.setResponseBody(responseBody);
        return httpLog;
    }
}
