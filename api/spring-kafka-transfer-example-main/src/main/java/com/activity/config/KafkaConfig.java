package com.activity.config;

import com.activity.exception.KafkaErrorHandler;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;

@Configuration
public class KafkaConfig {

    @Value("${topic.name}")
    private String topicName;

    @Bean
    public NewTopic createTopic(){
        return new NewTopic(topicName, 3, (short) 1);
    }


    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    @Autowired
    private CommonErrorHandler customCommonErrorHandler; // Menggunakan bean yang baru dibuat

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(customCommonErrorHandler);
        return factory;
    }

}
