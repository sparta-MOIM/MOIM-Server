package com.sparta.moim.session.shared.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
  @Bean
  public <T> ConsumerFactory<String, T> kafkaConsumer() {
    Map<String, Object> consumerConfig = new HashMap<>();
    consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    // 아래 두개는 등록하지 않아도 정상적으로 구동은 되어짐
    consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
    consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaConsumerFactory<>(consumerConfig);
  }

  // 이거 없으면 json으로 안 만들어줌
  @Bean
  public StringJsonMessageConverter jsonConverter() {
    return new StringJsonMessageConverter();
  }

  // 팩토리로 등록
  @Bean
  public <T> ConcurrentKafkaListenerContainerFactory<String, T> consumerCommonFiled() {
    ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(kafkaConsumer());
    return factory;
  }

  // 구체화
  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory() {
    return consumerCommonFiled();
  }
}
