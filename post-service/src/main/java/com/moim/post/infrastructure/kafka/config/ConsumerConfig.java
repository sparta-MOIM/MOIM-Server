package com.moim.post.infrastructure.kafka.config;

import com.google.common.collect.ImmutableMap;
import com.moim.post.infrastructure.kafka.event.FeedEvent;
import com.moim.post.infrastructure.kafka.event.VoteEvent;
import java.util.Map;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class ConsumerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${spring.kafka.consumer.auto-offset-reset}")
  private String autoOffsetReset;

  @Value("${spring.kafka.feed.group-id}")
  private String feedGroupId;

  @Value("${spring.kafka.vote.group-id}")
  private String voteGroupId;

  // KafkaListener 컨테이너 팩토리를 생성하는 Bean 메서드
  @Bean
  ConcurrentKafkaListenerContainerFactory<String, FeedEvent> feedEventContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, FeedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(feedEventConsumerFactory());
    return factory;
  }

  // Kafka ConsumerFactory를 생성하는 Bean 메서드
  @Bean
  public ConsumerFactory<String, FeedEvent> feedEventConsumerFactory() {
    JsonDeserializer<FeedEvent> deserializer = new JsonDeserializer<>();
    deserializer.addTrustedPackages("*");
    Map<String, Object> consumerConfigurations =
        ImmutableMap.<String, Object>builder()
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, feedGroupId)
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset)
            .build();

    return new DefaultKafkaConsumerFactory<>(consumerConfigurations, new StringDeserializer(), deserializer);
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, VoteEvent> voteEventContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, VoteEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(voteEventConsumerFactory());
    return factory;
  }

  @Bean
  public ConsumerFactory<String, VoteEvent> voteEventConsumerFactory() {
    JsonDeserializer<VoteEvent> deserializer = new JsonDeserializer<>();
    deserializer.addTrustedPackages("*");
    Map<String, Object> consumerConfigurations =
        ImmutableMap.<String, Object>builder()
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, voteGroupId)
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
            .put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset)
            .build();

    return new DefaultKafkaConsumerFactory<>(consumerConfigurations, new StringDeserializer(), deserializer);
  }

}
