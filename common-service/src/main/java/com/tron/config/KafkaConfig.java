package com.tron.config;


import com.tron.event.dto.PaymentCreatedEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializerWrapper;
import org.springframework.kafka.serializer.JsonDeserializer;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    private final String bootstrapServers = "localhost:9092";  // Kafka sunucunuzun adresi

    // Kafka Producer Configuration (JSON Serileştirme)
    @Bean
    public KafkaTemplate<String, PaymentCreatedEvent> kafkaTemplate() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        ProducerFactory<String, PaymentCreatedEvent> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);
        return new KafkaTemplate<>(producerFactory);
    }

    // Kafka Consumer Configuration (JSON Deserileştirme)
    @Bean
    public ConcurrentMessageListenerContainerFactory<String, PaymentCreatedEvent> messageListenerContainerFactory() {
        ConcurrentMessageListenerContainerFactory<String, PaymentCreatedEvent> factory = new ConcurrentMessageListenerContainerFactory<>();

        // Kafka consumer için deserialization yapılandırması
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put("bootstrap.servers", bootstrapServers);

        // Error handling, JsonDeserializer kullanıyoruz
        JsonDeserializer<PaymentCreatedEvent> deserializer = new JsonDeserializer<>(PaymentCreatedEvent.class);
        deserializer.addTrustedPackages("*");  // JSON deserialization için tüm paketlere güveniyoruz
        deserializer.setRemoveTypeHeaders(false);

        ConsumerFactory<String, PaymentCreatedEvent> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), deserializer);
        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}
