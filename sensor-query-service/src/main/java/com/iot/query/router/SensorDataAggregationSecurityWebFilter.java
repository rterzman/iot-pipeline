package com.iot.query.router;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class SensorDataAggregationSecurityWebFilter implements WebFilter {
    private static final Pattern PATH_SENSOR_PATTERN = Pattern.compile(".*iot.*\\bsensors\\b.*");

    @Value("${query.security.sensors.token}")
    private String sensorsApiToken;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();
        if (isSensorPath(path) && isFalse(isAuthorized(request))) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();
        }
        return chain.filter(exchange);
    }

    private boolean isSensorPath(String path) {
        return PATH_SENSOR_PATTERN.matcher(path).find();
    }

    private boolean isAuthorized(ServerHttpRequest request) {
        return Optional.of(request.getHeaders())
                .stream()
                .map(h -> h.get(HttpHeaders.AUTHORIZATION))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .anyMatch(v -> v.equals(sensorsApiToken));
    }
}
