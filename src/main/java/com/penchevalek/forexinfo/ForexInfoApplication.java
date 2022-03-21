package com.penchevalek.forexinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableRedisRepositories(basePackages = "com.penchevalek.forexinfo.repository.redis")
@EnableJpaRepositories(basePackages = "com.penchevalek.forexinfo.repository.jpa")
public class ForexInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForexInfoApplication.class, args);
    }
}
