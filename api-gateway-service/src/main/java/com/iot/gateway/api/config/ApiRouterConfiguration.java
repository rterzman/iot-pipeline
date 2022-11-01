package com.iot.gateway.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.iot.gateway.api.handler.SensorDataHandler;

@Configuration(proxyBeanMethods = false)
public class ApiRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> route(SensorDataHandler sensorDataHandler) {
        return RouterFunctions.route()
                .POST("/iot/api/event", RequestPredicates.contentType(MediaType.APPLICATION_JSON),
                        sensorDataHandler::handleEvent)
                .build();
    }

}
