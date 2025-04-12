package com.sparta.moim.session.shared.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
  @Value("${spring.kafka.bootstrap-servers}")
  private String kafkaBootstrapServers;
  @Bean
  public <T> ConsumerFactory<String, T> consumerFactory() {
    Map<String, Object> consumerConfig = new HashMap<>();
    consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
    // 아래 두개는 등록하지 않아도 정상적으로 구동은 되어짐
    consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(consumerConfig);
  }

 @Bean
  public RecordMessageConverter jsonConverter() {
    return new StringJsonMessageConverter();
  }

  // 팩토리로 등록
  @Bean
  public <T> ConcurrentKafkaListenerContainerFactory<String, T> consumerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.setRecordMessageConverter(jsonConverter());
    return factory;
  }

  // 구체화
  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Object> containerObjectFactory() {
    return consumerContainerFactory();
  }
}
