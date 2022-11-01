package com.iot.gateway.api.model;

public record SensorEventBusDto(String type, Long time, Double measurement)  {
}
