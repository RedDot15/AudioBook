package org.example.audiobook.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.example.audiobook.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("user")
    private User user;
}
