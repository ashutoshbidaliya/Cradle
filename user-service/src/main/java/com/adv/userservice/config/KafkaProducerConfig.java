package com.adv.userservice.config;

import com.adv.userservice.event.UserCreatedEvent;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public NewTopic topic() {
        return TopicBuilder.name("user-created-topic")
                .partitions(3)
                .replicas(1)
                .compact()
                .build();
    }

    @Bean
    public ProducerFactory<String, UserCreatedEvent> userCreatedEventProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerialize.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    public KafkaTemplate<String, UserCreatedEvent> userCreatedEventKafkaTemplate() {
        return new KafkaTemplate<>(userCreatedEventProducerFactory());
    }

}
