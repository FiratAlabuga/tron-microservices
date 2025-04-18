package com.tron.paymentservice.config;

import com.tron.config.KafkaConsumerConfig;
import com.tron.config.KafkaProducerConfig;
import com.tron.event.dto.PaymentCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@Import({KafkaProducerConfig.class, KafkaConsumerConfig.class}) // Common'dan import
public class PaymentKafkaConfig {
    private Logger logger = LoggerFactory.getLogger(PaymentKafkaConfig.class);

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentCreatedEvent>
    paymentCreatedListenerFactory(
            KafkaConsumerConfig commonConsumerConfig,
            KafkaTemplate<String, Object> dlqKafkaTemplate
    ) {
        return commonConsumerConfig.listenerContainerFactory(
                "payment-service-group", // GROUP_ID servis özel olacak
                PaymentCreatedEvent.class,
                dlqKafkaTemplate
        );
    }
}