package org.example.audiobook.dto.request.rating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RatingRequest {
    private String comment;

    private int rating;

    private UUID audioBookId;

    private UUID userId;
}