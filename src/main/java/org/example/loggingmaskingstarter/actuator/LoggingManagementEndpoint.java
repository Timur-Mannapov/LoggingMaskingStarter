package org.example.loggingmaskingstarter.actuator;

import org.example.loggingmaskingstarter.config.EndpointLoggingProperties;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(EndpointLoggingProperties.class)
public class LoggingManagementEndpoint {
    private final ApplicationContext context;
    private final EndpointLoggingProperties properties;
    public LoggingManagementEndpoint(ApplicationContext context, EndpointLoggingProperties properties) {
        this.context = context;
        this.properties = properties;
    }

    @Endpoint(id = "logging-masking")
    public static class MaskingEndpoint{

        private final ApplicationContext context;
        public MaskingEndpoint(ApplicationContext context) {
            this.context = context;
        }
        @ReadOperation
        public EndpointLoggingProperties getMaskingConfiguration() {
            return context.getBean(EndpointLoggingProperties.class);
        }

        @WriteOperation
        public EndpointLoggingProperties setMaskingConfiguration(Boolean maskingEnabled,  Map<String, EndpointLoggingProperties.MaskingConfig> maskingRules){
            Binder.get(context.getEnvironment()).bind("endpoint.logging", EndpointLoggingProperties.class)
                    .orElse(context.getBean(EndpointLoggingProperties.class))
                    .maskingRules();
            EndpointLoggingProperties properties = context.getBean(EndpointLoggingProperties.class);
            if(Objects.nonNull(maskingEnabled)) {
                properties.maskingEnabled(maskingEnabled);
            }
            if(Objects.nonNull(maskingRules)){
                properties.maskingRules(maskingRules);
            }
            return properties;
        }
    }

    @Endpoint(id = "logging-active")
    public static class ActiveEndpoint {
        private final ApplicationContext context;

        public ActiveEndpoint(ApplicationContext context) {
            this.context = context;
        }

        @ReadOperation
        public boolean getActiveConfiguration() {
            return context.getBean(EndpointLoggingProperties.class).active();
        }
        @WriteOperation
        public boolean setActiveConfiguration(@NonNull Boolean active){
            Assert.notNull(active, "Значение не может быть null");
            Binder.get(context.getEnvironment()).bind("endpoint.logging", EndpointLoggingProperties.class)
                    .orElse(context.getBean(EndpointLoggingProperties.class))
                    .active(active);
            return context.getBean(EndpointLoggingProperties.class).active();
        }
    }
}
