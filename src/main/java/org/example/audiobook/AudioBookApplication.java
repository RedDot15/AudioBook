package org.example.audiobook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.time.Instant;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.audiobook", "org.example.audiobook.config"})
@EnableScheduling
public class AudioBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(AudioBookApplication.class, args);
    }
}
