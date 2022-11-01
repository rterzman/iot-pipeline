package com.iot.query.handler;

import java.util.List;

import com.iot.query.model.AggregationType;
import com.iot.query.model.SensorAggregationResponseDto;
import com.iot.query.model.SensorGroupRequest;
import com.iot.query.repository.SensorDataAggregationRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public record SensorAggregationHandler(SensorDataAggregationRepository repository) {

    public Mono<SensorAggregationResponseDto> handleAggregation(String sensor, AggregationType type) {
        return executeAgg(type, sensor)
                .flatMap(r -> Mono.just(new SensorAggregationResponseDto(type, r)));
    }

    public Mono<SensorAggregationResponseDto> handleGroupAggregation(SensorGroupRequest request) {
        return executeGroupAgg(request).flatMap(r -> Mono.just(new SensorAggregationResponseDto(request.getType(), r)));
    }

    private Mono<Double> executeAgg(@NonNull AggregationType type, String sensor) {
        return switch (type) {
            case MIN -> repository.minByCriteria(sensor);
            case MAX -> repository.maxByCriteria(sensor);
            case AVG -> repository.avgByCriteria(sensor);
            case MEDIAN -> repository.medianByCriteria(sensor);
        };
    }

    private Mono<Double> executeGroupAgg(@NonNull SensorGroupRequest request) {
        List<String> preparedSensors = request.getSensors().stream().map(String::toUpperCase).toList();
        return switch (request.getType()) {
            case MIN ->
                    repository.minByGroupOfSensorsInTime(preparedSensors, request.startTime(), request.endTime());
            case MAX ->
                    repository.maxByGroupOfSensorsInTime(preparedSensors, request.startTime(), request.endTime());
            case AVG ->
                    repository.avgByGroupOfSensorsInTime(preparedSensors, request.startTime(), request.endTime());
            case MEDIAN -> repository.medianByGroupOfSensorsInTime(preparedSensors, request.startTime(),
                    request.endTime());
        };
    }

}
