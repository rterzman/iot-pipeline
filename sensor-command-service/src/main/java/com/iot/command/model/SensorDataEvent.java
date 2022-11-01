package com.iot.command.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SensorDataEvent(String type, Long time, Double measurement)  {
}
