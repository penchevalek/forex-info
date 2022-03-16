package com.penchevalek.forexinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ForexInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForexInfoApplication.class, args);
    }
}
