package com.sparta.moim.organization.infrastruct.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public <T> ProducerFactory<String, T> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        // 중간 구현
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // 동적으로 만들고
    @Bean
    public <T> KafkaTemplate<String, T> kafkaTemplate(ProducerFactory<String, T> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    // 구체화 시키고
//    @Bean
//    public KafkaTemplate<String, DemoCreateEvent> demoCreateEventKafkaTemplate() {
//        return kafkaTemplate(producerFactory());
//    }
}
