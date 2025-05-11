package org.example.audiobook.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.audiobook.entity.User;
import org.example.audiobook.service.UserService;
import org.example.audiobook.service.fcm.FCMService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendNotification {
    private final FCMService fcmService;
    private final UserService userService;
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(fixedRate = 60000)
    public void sendPeriodicNotifications() {
        try {
            log.info("Starting periodic notification job at: {}", LocalDateTime.now().format(formatter));
            
            // Lấy danh sách user có FCM token
            List<User> usersWithToken = userService.findUsersWithFCMToken();
            
            if (usersWithToken.isEmpty()) {
                log.info("No users with FCM tokens found");
                return;
            }

            String title = "AudioBook Update";
            String body = "Hãy khám phá những sách mới nhất của chúng tôi! " + 
                         LocalDateTime.now().format(formatter);

            int successCount = 0;
            for (User user : usersWithToken) {
                try {
                    if (user.getFcmToken() != null && !user.getFcmToken().isEmpty()) {
                        log.info("fcm token "+ user.getFcmToken());
                        fcmService.sendNotification(title, body, user.getFcmToken());
                        successCount++;
                    }
                } catch (Exception e) {
                    log.error("Failed to send notification to user {}: {}", 
                             user.getId(), e.getMessage());
                }
            }

            log.info("Completed sending notifications. Success: {}/{}", 
                    successCount, usersWithToken.size());
            
        } catch (Exception e) {
            log.error("Error in periodic notification job: {}", e.getMessage());
        }
    }
}
