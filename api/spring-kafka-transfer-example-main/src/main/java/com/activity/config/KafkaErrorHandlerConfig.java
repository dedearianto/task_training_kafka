package com.activity.config;

import com.activity.exception.KafkaErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.CommonErrorHandler;

@Configuration
public class KafkaErrorHandlerConfig {

    @Bean
    public CommonErrorHandler customCommonErrorHandler() {
        return new KafkaErrorHandler();
    }
}
