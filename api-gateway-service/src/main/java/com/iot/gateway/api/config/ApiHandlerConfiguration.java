package com.iot.gateway.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.iot.gateway.api.eventbus.EventBusProducer;
import com.iot.gateway.api.handler.SensorDataHandler;

@Configuration
public class ApiHandlerConfiguration {
    @Bean
    public SensorDataHandler sensorDataHandler(EventBusProducer eventBusProducer) {
        return new SensorDataHandler(eventBusProducer);
    }
}
