package com.tron.apartmentservice.config;

import com.tron.config.KafkaConsumerConfig;
import com.tron.config.KafkaProducerConfig;
import com.tron.event.dto.PaymentCompletedEvent;
import com.tron.event.dto.PaymentRollbackEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@Import({KafkaProducerConfig.class, KafkaConsumerConfig.class})
public class ApartmentKafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentCompletedEvent>
    paymentCompletedListenerFactory(
            KafkaConsumerConfig commonConsumerConfig,
            KafkaTemplate<String, Object> dlqKafkaTemplate
    ) {
        return commonConsumerConfig.listenerContainerFactory(
                "apartment-service-group", // Bu servise özel groupId
                PaymentCompletedEvent.class,
                dlqKafkaTemplate
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentRollbackEvent>
    paymentRollbackListenerFactory(
            KafkaConsumerConfig commonConsumerConfig,
            KafkaTemplate<String, Object> dlqKafkaTemplate
    ) {
        return commonConsumerConfig.listenerContainerFactory(
                "apartment-service-group", // Aynı grup içinde 2 listener
                PaymentRollbackEvent.class,
                dlqKafkaTemplate
        );
    }
}
