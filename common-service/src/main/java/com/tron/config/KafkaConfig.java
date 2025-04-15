package com.tron.config;

import com.tron.event.dto.PaymentCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    private final String bootstrapServers = "localhost:9092";

    // Kafka Producer Configuration
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        ProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);
        return new KafkaTemplate<>(producerFactory);
    }

    // Kafka Consumer Configuration
    @Bean
    public MessageListenerContainer messageListenerContainer() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Kafka'ya gelen mesajları deseralize etmek için JSON deserializer kullanıyoruz
        JsonDeserializer<PaymentCreatedEvent> valueDeserializer = new JsonDeserializer<>(PaymentCreatedEvent.class);
        valueDeserializer.addTrustedPackages("*");
        valueDeserializer.setRemoveTypeHeaders(false);

        DefaultKafkaConsumerFactory<String, PaymentCreatedEvent> consumerFactory =
                new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), valueDeserializer);

        // Kafka'dan gelen mesajları işlemek için MessageListener
        MessageListener<String, PaymentCreatedEvent> messageListener = new MessageListener<String, PaymentCreatedEvent>() {
            @Override
            public void onMessage(ConsumerRecord<String, PaymentCreatedEvent> record) {
                // Gelen mesajı burada işleyebilirsiniz
                System.out.println("Received message: " + record.value());
            }
        };

        // Kafka Consumer Container
        ContainerProperties containerProps = new ContainerProperties("payment-topic");
        containerProps.setMessageListener(messageListener);

        // MessageListenerContainer ile Kafka Consumer'ı başlatıyoruz
        return new ConcurrentMessageListenerContainer<>(consumerFactory, containerProps);
    }
}
