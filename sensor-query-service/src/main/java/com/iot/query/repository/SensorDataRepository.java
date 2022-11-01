package com.iot.query.repository;

import java.awt.print.Pageable;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.iot.query.model.SensorDataEntity;

import reactor.core.publisher.Flux;

/**
 * There Must be a separate sensor dictionary for storing uniq sensors
 */
public interface SensorDataRepository extends R2dbcRepository<SensorDataEntity, UUID> {

    @Query("SELECT DISTINCT upper(sensor) FROM sensors.event_data")
    Flux<String> findDistinctSensors();
}
