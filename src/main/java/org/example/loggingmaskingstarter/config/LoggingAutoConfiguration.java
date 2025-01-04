package org.example.loggingmaskingstarter.config;

import org.example.loggingmaskingstarter.core.LoggingInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
@EnableConfigurationProperties(EndpointLoggingProperties.class)
@ConditionalOnProperty(prefix = "endpoint.logging", value = "active", havingValue = "true")
public class LoggingAutoConfiguration {

    @Bean
    @ConditionalOnExpression("${endpoint.logging.active:false}")
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

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
