package com.iot.query.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.iot.query.handler.SensorAggregationHandler;
import com.iot.query.repository.SensorDataAggregationRepository;

@Configuration
public class SensorHandlerConfiguration {

    @Bean
    public SensorAggregationHandler sensorAggregationHandler(SensorDataAggregationRepository repository) {
        return new SensorAggregationHandler(repository);
    }
}
