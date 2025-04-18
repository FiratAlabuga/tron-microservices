package com.tron.config;

import com.tron.event.dto.PaymentCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig extends BaseKafkaConfig {

    // Tüm servislerin kullanabileceği generic consumer factory
    public <T> ConsumerFactory<String, T> consumerFactory(String groupId, Class<T> eventType) {
        Map<String, Object> props = consumerConfigs(groupId); // BaseKafkaConfig'den geliyor

        JsonDeserializer<T> deserializer = new JsonDeserializer<>(eventType);
        deserializer.addTrustedPackages("com.tron.event.dto.*"); // Tüm event paketlerine izin

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    // DLQ ve retry mekanizmalı generic listener container
    public <T> ConcurrentKafkaListenerContainerFactory<String, T> listenerContainerFactory(
            String groupId,
            Class<T> eventType,
            KafkaTemplate<String, Object> dlqKafkaTemplate // DLQ için
    ) {
        ConcurrentKafkaListenerContainerFactory<String, T> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory(groupId, eventType));

        // DLQ ve Retry Konfigürasyonu
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(
                        dlqKafkaTemplate,
                        (record, ex) -> new TopicPartition(record.topic() + ".DLQ", record.partition())
                ),
                new FixedBackOff(5000, 3) // 5 sn'de bir, max 3 deneme
        );
        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }
}

//    private final String bootstrapServers = "localhost:9092";
//
//    // DLQ topic adı
//    private static final String DLQ_TOPIC_SUFFIX = ".DLQ";
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, PaymentCreatedEvent> kafkaListenerContainerFactory(
//            ConsumerFactory<String, PaymentCreatedEvent> consumerFactory,
//            KafkaTemplate<String, Object> kafkaTemplate) {
//
//        // ErrorHandler için DLQ ayarları
//        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
//                new DeadLetterPublishingRecoverer(kafkaTemplate,
//                        (r, e) -> new TopicPartition(r.topic() + DLQ_TOPIC_SUFFIX, r.partition())),
//                new FixedBackOff(3000L, 3) // 3 saniye bekle, 3 kere dene
//        );
//
//        // Kafka Listener Factory'i oluşturuyoruz
//        ConcurrentKafkaListenerContainerFactory<String, PaymentCreatedEvent> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
//        factory.setCommonErrorHandler(errorHandler);  // ErrorHandler'ı ekliyoruz
//
//        return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<String, PaymentCreatedEvent> consumerFactory() {
//        Map<String, Object> consumerProps = new HashMap<>();
//        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
//        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//
//        JsonDeserializer<PaymentCreatedEvent> valueDeserializer = new JsonDeserializer<>(PaymentCreatedEvent.class);
//        valueDeserializer.addTrustedPackages("*");
//        valueDeserializer.setRemoveTypeHeaders(false);
//
//        return new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), valueDeserializer);
//    }
//
//    @Bean
//    public MessageListenerContainer messageListenerContainer(ConcurrentKafkaListenerContainerFactory<String, PaymentCreatedEvent> factory) {
//        ContainerProperties containerProps = new ContainerProperties("payment-topic");
//
//        // Kafka'dan gelen mesajları işlemek için MessageListener
//        MessageListener<String, PaymentCreatedEvent> messageListener = new MessageListener<String, PaymentCreatedEvent>() {
//            @Override
//            public void onMessage(ConsumerRecord<String, PaymentCreatedEvent> record) {
//                // Gelen mesajı burada işleyebilirsiniz
//                System.out.println("Received message: " + record.value());
//                // İşlem başarısız olursa exception fırlatılabilir
//                // Örneğin:
//                // throw new RuntimeException("Simulated error");
//            }
//        };
//
//        // Kafka Consumer Container
//        containerProps.setMessageListener(messageListener);
//
//        // Consumer'ı başlatıyoruz
//        return new ConcurrentMessageListenerContainer<>(consumerFactory(), containerProps);
//    }

