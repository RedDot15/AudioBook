package org.example.audiobook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @JsonProperty("username")
    private String username;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("premium_status")
    private Boolean premiumStatus;
}
