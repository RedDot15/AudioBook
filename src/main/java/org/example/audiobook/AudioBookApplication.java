package org.example.audiobook;


import org.example.audiobook.component.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AudioBookApplication {


    public static void main(String[] args) {
        SpringApplication.run(AudioBookApplication.class, args);
    }





}
