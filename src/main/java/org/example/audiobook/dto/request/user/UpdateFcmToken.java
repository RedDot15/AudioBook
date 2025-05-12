package org.example.audiobook.dto.request.user;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateFcmToken {
    private String fcmToken;

    private String userId;
}
