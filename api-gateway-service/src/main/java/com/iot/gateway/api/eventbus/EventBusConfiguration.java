package com.iot.gateway.api.eventbus;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.gateway.api.model.SensorEventBusDto;

import lombok.NonNull;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class EventBusConfiguration {

    @Bean
    public ReactiveKafkaProducerTemplate<String, SensorEventBusDto> reactiveKafkaProducerTemplate(@NonNull KafkaProperties props) {
        Map<String, Object> propMap = props.buildProducerProperties();
        propMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(propMap));
    }

    @Bean
    public EventBusProducer eventProducer(
            @Value("${eventbus.kafka.producer.topic:iot_pipeline_data}") String eventBusTopic,
            ObjectMapper objectMapper,
            ReactiveKafkaProducerTemplate<String, SensorEventBusDto> reactiveKafkaProducerTemplate) {
        return new EventBusProducer(eventBusTopic, reactiveKafkaProducerTemplate, objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
