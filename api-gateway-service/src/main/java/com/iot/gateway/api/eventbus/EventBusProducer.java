package com.iot.gateway.api.eventbus;

import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.gateway.api.model.SensorEventBusDto;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public record EventBusProducer(@NonNull String eventBusTopic,
                               @NonNull ReactiveKafkaProducerTemplate<String, SensorEventBusDto> reactiveTemplate,
                               ObjectMapper objectMapper) {

    @SneakyThrows
    public Mono<Void> sendEvent(SensorEventBusDto event) {
        var message = MessageBuilder.withPayload(event).build();
        return reactiveTemplate.send(eventBusTopic, message)
                .doOnSuccess(r -> log.info("Event was sent successfully"))
                .doOnError(r -> log.error("Failed to send event={}, cause=", event, r))
                .then();
    }
}
