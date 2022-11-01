package com.iot.command.repository;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.iot.command.model.SensorDataEntity;

public interface SensorsDataRepository extends R2dbcRepository<SensorDataEntity, UUID> {
}
