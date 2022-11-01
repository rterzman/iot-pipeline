package com.iot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = "com.iot.command.repository")
public class SensorCommandServiceApplication {


    @Autowired
    ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(SensorCommandServiceApplication.class, args);
    }

    @Bean
    public String v() {
        return "b";
    }
}
