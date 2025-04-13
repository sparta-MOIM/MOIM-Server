package com.sparta.moim.organization.infrastruct.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public <T> ConsumerFactory<String, T> kafkaConsumer() {
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        consumerConfig.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        consumerConfig.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        consumerConfig.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
        consumerConfig.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 10000);
        consumerConfig.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 40000);
        return new DefaultKafkaConsumerFactory<>(consumerConfig);
    }

    // 이거 없으면 json으로 역직렬화가 안됨
    @Bean
    public StringJsonMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }

    // container 등록
    @Bean
    public <T> ConcurrentKafkaListenerContainerFactory<String, T> consumerCommonFiled() {
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumer());
        return factory;
    }

    // 구체화
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> containerObjectFactory() {
        return consumerCommonFiled();
    }
}
