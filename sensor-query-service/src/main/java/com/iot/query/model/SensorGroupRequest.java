package com.iot.query.model;

import static java.util.Objects.isNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SensorGroupRequest {
    @NotNull
    @Size(min = 1)
    private List<String> sensors;

    @NotNull
    private Long fromTime;
    private Long toTime;

    @NotNull
    private AggregationType type;

    public ZonedDateTime endTime() {
        if (isNull(toTime)) {
            return ZonedDateTime.now(ZoneId.of("UTC"));
        }
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(toTime), ZoneId.of("UTC"));
    }

    public ZonedDateTime startTime() {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(fromTime), ZoneId.of("UTC"));
    }
}
