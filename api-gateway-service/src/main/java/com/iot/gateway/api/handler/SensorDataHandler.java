package com.iot.gateway.api.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.iot.gateway.api.eventbus.EventBusProducer;
import com.iot.gateway.api.model.SensorDataRequestDto;
import com.iot.gateway.api.model.SensorEventBusDto;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public record SensorDataHandler(EventBusProducer producer) {
    public Mono<ServerResponse> handleEvent(ServerRequest request) {
        return request.bodyToMono(SensorDataRequestDto.class)
                .flatMap(this::toEvent)
                .flatMap(producer::sendEvent)
                .flatMap(r -> ServerResponse.ok().build());
    }

    private Mono<SensorEventBusDto> toEvent(SensorDataRequestDto dto) {
        return Mono.just(new SensorEventBusDto(dto.sensor(), dto.time(), dto.measurement()));
    }
}
