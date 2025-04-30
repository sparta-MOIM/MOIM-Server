package com.sparta.moim.chat.infrastructure.config.kafka;


import com.google.common.collect.ImmutableMap;
import com.sparta.moim.chat.application.dto.ChatRoomResponseDTO;
import com.sparta.moim.chat.presentation.request.MessageSendDTO;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

//kafka producer 설정 클래스를 작성합니다.
@EnableKafka
@Configuration
public class ProducerConfiguration {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  // Kafka ProducerFactory를 생성하는 Bean 메서드
  @Bean
  public ProducerFactory<String, MessageSendDTO> messageSendProducerFactory() {
    return new DefaultKafkaProducerFactory<>(messageSendProducerConfigurations());
  }

  // Kafka Producer 구성을 위한 설정값들을 포함한 맵을 반환하는 메서드
  @Bean
  public Map<String, Object> messageSendProducerConfigurations() {
    return ImmutableMap.<String, Object>builder()
        .put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        .put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
        .put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
        .build();
  }

  // KafkaTemplate을 생성하는 Bean 메서드
  @Bean
  public KafkaTemplate<String, MessageSendDTO> messageSendKafkaTemplate() {
    return new KafkaTemplate<>(messageSendProducerFactory());
  }

}
