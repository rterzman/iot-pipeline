package com.iot.query.model;

import static java.util.Objects.isNull;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AggregationType {
    MIN, MAX, AVG, MEDIAN;

    @JsonCreator
    public static AggregationType fromValue(String val) {
        return valueOfType(val);
    }

    public static AggregationType valueOfType(String type) {
        if (isNull(type)) {
            return null;
        }
        return Arrays.stream(AggregationType.values())
                .filter(t -> t.name().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }

}
