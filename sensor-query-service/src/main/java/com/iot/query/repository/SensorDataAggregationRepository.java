package com.iot.query.repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iot.query.model.SensorDataEntity;

import reactor.core.publisher.Mono;

@Repository
public interface SensorDataAggregationRepository extends R2dbcRepository<SensorDataEntity, UUID> {

    @Query("select MIN(measurement) from sensors.event_data WHERE upper(sensor) = upper(:sensor)")
    Mono<Double> minByCriteria(@Param("sensor") String sensor);

    @Query("select MAX(measurement) from sensors.event_data WHERE upper(sensor) = upper(:sensor)")
    Mono<Double> maxByCriteria(@Param("sensor") String sensor);

    @Query("select AVG(measurement) from sensors.event_data WHERE upper(sensor) = upper(:sensor)")
    Mono<Double> avgByCriteria(@Param("sensor") String sensor);

    @Query("select PERCENTILE_CONT(0.5) WITHIN GROUP(ORDER BY measurement ) "
            + "from sensors.event_data WHERE upper(sensor) = upper(:sensor)")
    Mono<Double> medianByCriteria(@Param("sensor") String sensor);

    @Query("select MIN(measurement) from sensors.event_data where upper(sensor) IN (:sensors) AND ts > :startTs AND ts < :endTs")
    Mono<Double> minByGroupOfSensorsInTime(List<String> sensors, ZonedDateTime startTs, ZonedDateTime endTs);

    @Query("select MAX(measurement) from sensors.event_data where upper(sensor) IN (:sensors) AND ts > :startTs AND ts < :endTs")
    Mono<Double> maxByGroupOfSensorsInTime(List<String> sensors, ZonedDateTime startTs, ZonedDateTime endTs);

    @Query("select AVG(measurement) from sensors.event_data where upper(sensor) IN (:sensors) AND ts > :startTs AND ts < :endTs")
    Mono<Double> avgByGroupOfSensorsInTime(List<String> sensors, ZonedDateTime startTs, ZonedDateTime endTs);

    @Query("select PERCENTILE_CONT(0.5) WITHIN GROUP(ORDER BY measurement ) from sensors.event_data "
            + "where upper(sensor) IN (:sensors) AND ts > :startTs AND ts < :endTs")
    Mono<Double> medianByGroupOfSensorsInTime(List<String> sensors, ZonedDateTime startTs, ZonedDateTime endTs);

}
