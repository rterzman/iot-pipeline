package com.iot.gateway.api.model;

public record SensorDataRequestDto(String sensor, Long time, Double measurement) {
}
