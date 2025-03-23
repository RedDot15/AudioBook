package org.example.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "userPreniums")
public class UserPrenium {
    @Id
    @GeneratedValue
    @UuidGenerator
    UUID id;

    @ManyToOne
    User user;

    @ManyToOne
    PreniumPlan preniumPlan;

    LocalDateTime startDate;

    LocalDateTime endDate;

    PaymentStatus paymentStatus;

}
