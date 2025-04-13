package com.sparta.moim.organization.infrastruct.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
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

        // Kafka 브로커의 주소 (호스트:포트)
        // 여러 브로커가 있을 경우 쉼표(,)로 구분해서 여러 개 설정 가능
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // 메시지의 Key 직렬화 클래스 설정 (여기서는 String 타입 직렬화)
        // Kafka가 네트워크 전송 시 데이터를 바이너리 형식으로 직렬화해야 함
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // 메시지의 Value 직렬화 클래스 설정 (여기서는 JSON 직렬화)
        // 객체를 JSON 형식으로 직렬화해 Kafka로 전송
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonSerializer.class);

        // 메시지 전송 시 브로커로부터 ACK(응답)를 받을 조건 설정
        // "all": 모든 리플리카가 메시지를 저장해야 ACK (가장 높은 신뢰성)
        // "1": 리더 브로커만 저장해도 ACK (속도와 신뢰성의 균형)
        // "0": 브로커의 ACK 없이 전송 (가장 빠르지만 메시지 손실 위험)
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");

        // 메시지 전송 실패 시 재시도 횟수
        // 네트워크 장애나 브로커 장애 시 몇 번까지 재시도할지 설정
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);

        // 메시지를 전송하기 전 최대 대기 시간 (ms 단위)
        // 설정한 시간 동안 메시지를 모아서 배치로 전송 (네트워크 효율 향상)
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 100);

        // 한 번에 전송할 메시지 배치의 최대 크기 (바이트 단위)
        // 배치 전송으로 네트워크 효율을 높임 (여기서는 16KB)
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);

        // 프로듀서의 메시지 버퍼 메모리 크기 (바이트 단위)
        // Kafka로 전송하기 전 메시지를 임시 저장하는 버퍼 크기 (여기서는 32MB)
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        // 메시지 압축 방식 설정
        // 네트워크 트래픽 절감을 위해 메시지를 압축해 전송
        // 각 알고리즘의 특징:
        // "gzip": 높은 압축률, 느린 속도 (CPU 사용량 높음)
        // "snappy": 빠른 속도, 낮은 압축률 (CPU 사용량 낮음, 지연 시간 최소화)
        // "lz4": snappy보다 약간 높은 압축률, 빠른 속도 (빠른 압축 해제 속도)
        // "zstd": 높은 압축률과 빠른 속도의 균형 (Kafka 2.1.0 이상에서 지원)
        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");

        // 프로듀서를 식별하는 고유한 ID 설정
        // Kafka 클러스터에서 프로듀서를 구분하고 모니터링할 때 유용
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, "my-producer");
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
