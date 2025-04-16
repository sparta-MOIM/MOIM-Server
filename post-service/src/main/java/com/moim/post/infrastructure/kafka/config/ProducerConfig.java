package com.moim.post.infrastructure.kafka.config;


import com.google.common.collect.ImmutableMap;
import com.moim.post.application.event.PostNotificationEvent;
import java.util.Map;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class ProducerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  public ProducerFactory<String, PostNotificationEvent> messageSendProducerFactory() {
    return new DefaultKafkaProducerFactory<>(messageSendProducerConfigurations());
  }

  @Bean
  public Map<String, Object> messageSendProducerConfigurations() {
    return ImmutableMap.<String, Object>builder()
        .put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        .put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
        .put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
        .build();
  }

  @Bean
  public KafkaTemplate<String, PostNotificationEvent> messageSendKafkaTemplate() {
    return new KafkaTemplate<>(messageSendProducerFactory());
  }

}
