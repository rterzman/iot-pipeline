package com.iot.command.config;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.iot.command.handler.SensorsEventDataHandler;
import com.iot.command.model.SensorDataEvent;
import com.iot.command.repository.SensorsDataRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.ReceiverOptions;

@Slf4j
@Configuration
public class SensorsEventHandlerConfiguration {

    @Bean
    public ReceiverOptions<String, SensorDataEvent> sensorEventReceiverOptions(KafkaProperties props,
            @Value("${command.kafka.eventbus.sensors.topic-name}") String topicName) {
        ReceiverOptions<String, SensorDataEvent> receiverOptions = ReceiverOptions.create(
                props.buildConsumerProperties());
        return receiverOptions.subscription(List.of(topicName))
                .withValueDeserializer(new JsonDeserializer<>(SensorDataEvent.class))
                .withKeyDeserializer(new StringDeserializer());
    }

    @Bean
    public SensorsEventDataHandler sensorsEventDataHandler(SensorsDataRepository repository) {
        return new SensorsEventDataHandler(repository);
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, SensorDataEvent> reactiveKafkaConsumerTemplate(
            ReceiverOptions<String, SensorDataEvent> sensorEventReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(sensorEventReceiverOptions);
    }

    @Bean
    public ApplicationRunner sensorsConsumerRunner(
            ReactiveKafkaConsumerTemplate<String, SensorDataEvent> reactiveKafkaConsumerTemplate,
            SensorsEventDataHandler sensorsEventDataHandler) {
        return args -> reactiveKafkaConsumerTemplate.receiveAutoAck()
                .doOnNext(record -> log.info("Received record={}", record.toString()))
                .map(ConsumerRecord::value)
                .doOnNext(sensorsEventDataHandler::handler)
                .doOnNext(e -> log.debug("Event was successfully created, event={}", e))
                .doOnError(t -> log.error("Failed to handle event, ", t))
                .subscribe();
    }

}
