package com.tron.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig extends BaseKafkaConfig {
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }
}

//    private final String bootstrapServers = "localhost:9092";
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate() {
//        Map<String, Object> producerProps = new HashMap<>();
//        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // JSON serileştirme
//        producerProps.put(ProducerConfig.ACKS_CONFIG, "all"); // Yüksek güvenilirlik
//        producerProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // Exactly-once için
//        producerProps.put(ProducerConfig.RETRIES_CONFIG, 3); // Retry mekanizması
//        producerProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy"); // Performans
//
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerProps));
//        Map<String, Object> producerProps = new HashMap<>();
//        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//        ProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);
//        return new KafkaTemplate<>(producerFactory);