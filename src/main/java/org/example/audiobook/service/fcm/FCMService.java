package org.example.audiobook.service.fcm;

import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FCMService {

    public void sendNotification(String title, String body, String token) {
        try {
            // Tạo notification
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            // Thêm data payload để tracking và xử lý khi app ở background
            Map<String, String> data = new HashMap<>();
            data.put("timestamp", String.valueOf(System.currentTimeMillis()));
            data.put("click_action", "OPEN_MAIN_ACTIVITY");

            // Cấu hình cho Android với priority cao
            AndroidConfig androidConfig = AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH) // Priority cao cho Android
                    .setTtl(60 * 1000) // Time to live: 1 phút
                    .setDirectBootOk(true) // Cho phép nhận khi device đang ở direct boot mode
                    .build();

            // Tạo message - không dùng setPriority trực tiếp
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .putAllData(data) // Thêm data
                    .setAndroidConfig(androidConfig) // Priority đã được đặt trong androidConfig
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent notification with high priority. Response: " + response);
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send notification: {}", e.getMessage(), e);
        }
    }

    public void sendNotificationToMultipleTokens(String title, String body, List<String> tokens) {
        try {
            MulticastMessage message = MulticastMessage.builder()
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .addAllTokens(tokens)
                    .build();

            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            log.info("Successfully sent notification to {} recipients", response.getSuccessCount());
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send notification to multiple tokens", e);
        }
    }
} 