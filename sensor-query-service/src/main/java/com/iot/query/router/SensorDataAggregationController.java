package com.iot.query.router;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iot.query.handler.SensorAggregationHandler;
import com.iot.query.model.AggregationType;
import com.iot.query.model.SensorAggregationResponseDto;
import com.iot.query.model.SensorGroupRequest;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/iot/aggregation/sensors")
public class SensorDataAggregationController {
    private final SensorAggregationHandler handler;

    @GetMapping(value = "/{sensor}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<SensorAggregationResponseDto> aggregatePerSource(@PathVariable("sensor") @NonNull String sensor,
            @RequestParam("type") AggregationType type, @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        return handler.handleAggregation(sensor, type);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<SensorAggregationResponseDto> aggregatePerRequest(@RequestBody SensorGroupRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        return handler.handleGroupAggregation(request);
    }
}
