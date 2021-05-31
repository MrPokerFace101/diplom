package ru.itis.tracing.framework.cfg;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itis.tracing.framework.processors.TraceAnnotationProcessor;

@Configuration
public class TracingConfig {

    @Bean
    @ConditionalOnProperty(name = "tracing", value = "true")
    public TraceAnnotationProcessor traceAnnotationProcessor() {
        return new TraceAnnotationProcessor();
    }
}
