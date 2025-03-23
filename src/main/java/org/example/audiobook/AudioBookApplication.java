package org.example.audiobook;

import org.example.audiobook.entity.PaymentStatus;
import org.example.audiobook.entity.User;
import org.example.audiobook.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AudioBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(AudioBookApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            // Create and save an entity
            User entity = User.builder()
                    .paymentStatus(PaymentStatus.DAMN)
                    .build();
            repository.save(entity);

            // Retrieve and print the saved entity
            User savedEntity = repository.findById(entity.getId()).orElse(null);
            System.out.println("Saved Entity ID: " + savedEntity.getId());
            System.out.println("Saved Entity payment status: " + savedEntity.getPaymentStatus().getStatus2());
        };
    }
}
