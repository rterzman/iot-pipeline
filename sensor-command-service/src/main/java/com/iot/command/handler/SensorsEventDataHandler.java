package com.iot.command.handler;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.iot.command.model.SensorDataEntity;
import com.iot.command.model.SensorDataEvent;
import com.iot.command.repository.SensorsDataRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public record SensorsEventDataHandler(SensorsDataRepository repository) {

    public Mono<SensorDataEntity> handler(SensorDataEvent event) {
        log.debug("Processing sensor event, type={}, time={}", event.type(), event.time());
        return this.repository.save(toEntity(event)).onErrorResume(th -> {
            log.error("Storing event is failed, type={}, time={}", event.type(), event.time(), th);
            return Mono.error(th);
        });
    }

    private static SensorDataEntity toEntity(SensorDataEvent event) {
        ZonedDateTime eventDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(event.time()), ZoneId.of("UTC"));
        return new SensorDataEntity().setSensor(event.type()).setTs(eventDateTime).setValue(event.measurement());
    }

}
