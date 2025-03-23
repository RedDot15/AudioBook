package org.example.audiobook.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "pernium_plans")
public class PreniumPlan {
    @Id
    @GeneratedValue
    @UuidGenerator
    UUID id;

    String name;

    BigDecimal price;

    Integer durationDays;

    String description;
}
