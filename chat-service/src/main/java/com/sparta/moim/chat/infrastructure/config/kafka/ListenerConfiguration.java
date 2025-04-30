package com.sparta.moim.chat.infrastructure.config.kafka;

import com.sparta.moim.chat.application.dto.ChatRoomResponseDTO;
import com.sparta.moim.chat.presentation.request.MessageSendDTO;
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
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.google.common.collect.ImmutableMap;


// Kafka consumer 설정 클래스를 작성합니다.
@EnableKafka
@Configuration
public class ListenerConfiguration {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${spring.kafka.consumer.auto-offset-reset}")
  private String autoOffsetReset;

  @Value("${spring.kafka.chat.group-id}")
  private String groupId;


  // KafkaListener 컨테이너 팩토리를 생성하는 Bean 메서드
  @Bean
  ConcurrentKafkaListenerContainerFactory<String, MessageSendDTO> kafkaMessageSendContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, MessageSendDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(messageSendconsumerFactory());
    return factory;
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, ChatRoomResponseDTO> kafkaChatRoomContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, ChatRoomResponseDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(chatRoomconsumerFactory());
    return factory;
  }

  // Kafka ConsumerFactory를 생성하는 Bean 메서드
  @Bean
  public ConsumerFactory<String, MessageSendDTO> messageSendconsumerFactory() {
    JsonDeserializer<MessageSendDTO> deserializer = new JsonDeserializer<>();
    // 패키지 신뢰 오류로 인해 모든 패키지를 신뢰하도록 작성
    deserializer.addTrustedPackages("*");

    // Kafka Consumer 구성을 위한 설정값들을 설정 -> 변하지 않는 값이므로 ImmutableMap을 이용하여 설정
    // Kafka Consumer 설정값은 일반적으로 애플리케이션 실행 중에 변경되지 않아야 하는 고정된 구성값이어서 ImmutableMap을 사용해야 함
    Map<String, Object> consumerConfigurations =
        ImmutableMap.<String, Object>builder()
            .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
            .put(ConsumerConfig.GROUP_ID_CONFIG,groupId)
            .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
            .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
            .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset)
            .build();

    return new DefaultKafkaConsumerFactory<>(consumerConfigurations , new StringDeserializer(), deserializer);
  }

  @Bean
  public ConsumerFactory<String, ChatRoomResponseDTO> chatRoomconsumerFactory() {
    JsonDeserializer<ChatRoomResponseDTO> deserializer = new JsonDeserializer<>();
    // 패키지 신뢰 오류로 인해 모든 패키지를 신뢰하도록 작성
    deserializer.addTrustedPackages("*");

    // Kafka Consumer 구성을 위한 설정값들을 설정 -> 변하지 않는 값이므로 ImmutableMap을 이용하여 설정
    // Kafka Consumer 설정값은 일반적으로 애플리케이션 실행 중에 변경되지 않아야 하는 고정된 구성값이어서 ImmutableMap을 사용해야 함
    Map<String, Object> consumerConfigurations =
        ImmutableMap.<String, Object>builder()
            .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
            .put(ConsumerConfig.GROUP_ID_CONFIG,"chat-group")
            .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
            .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
            .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset)
            .build();

    return new DefaultKafkaConsumerFactory<>(consumerConfigurations, new StringDeserializer(), deserializer);
  }



}