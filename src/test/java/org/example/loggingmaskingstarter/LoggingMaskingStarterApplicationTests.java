package org.example.loggingmaskingstarter;

import org.example.loggingmaskingstarter.config.LoggingAutoConfiguration;
import org.example.loggingmaskingstarter.core.LoggingInterceptor;
import org.example.loggingmaskingstarter.exception.StarterException;
import org.example.loggingmaskingstarter.logging.LoggingEnvironmentPostProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class LoggingMaskingStarterApplicationTests {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(LoggingAutoConfiguration.class));

    @Test
    void loggingInterceptorIsRegisteredWhenActiveIsTrue() {
        contextRunner
                .withPropertyValues("endpoint.logging.active=true")
                .run(context -> {
                    assertThat(context).hasSingleBean(LoggingInterceptor.class);
                    assertThat(context).hasSingleBean(WebMvcConfigurer.class);

                    WebMvcConfigurer configurer = context.getBean(WebMvcConfigurer.class);
                    InterceptorRegistry registry = mock(InterceptorRegistry.class);
                    configurer.addInterceptors(registry);
                    verify(registry).addInterceptor(any(HandlerInterceptor.class));
                });
    }

    @Test
    void loggingInterceptorIsNotRegisteredWhenActiveIsFalse() {
        contextRunner
                .withPropertyValues("endpoint.logging.active=false")
                .run(context -> {
                    assertThat(context).doesNotHaveBean(LoggingInterceptor.class);
                    assertThat(context).doesNotHaveBean(WebMvcConfigurer.class);
                });
    }

    @Test
    void whenInvalidProperty_thenThrowLoggingStartupException() {
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.getPropertySources().addFirst(new MapPropertySource("test", Collections.singletonMap("endpoint.logging.active", "exception")));

        LoggingEnvironmentPostProcessor processor = new LoggingEnvironmentPostProcessor();

        assertThatExceptionOfType(StarterException.class)
                .isThrownBy(() -> processor.postProcessEnvironment(environment, new SpringApplication()))
                .withMessageContaining("Ошибка во время проверки свойства 'endpoint.logging.active'");
    }
}
