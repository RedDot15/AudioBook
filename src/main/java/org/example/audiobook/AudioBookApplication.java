package org.example.audiobook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.time.Instant;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.audiobook", "org.example.audiobook.config"})
public class AudioBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(AudioBookApplication.class, args);
    }
}
