package com.iot.query.router;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iot.query.repository.SensorDataRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/iot/sensors")
public class SensorDataController {
    private final SensorDataRepository repository;

    @GetMapping(value = "", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<List<String>> getAllSensors(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        return repository.findDistinctSensors().collectList();
    }
}
