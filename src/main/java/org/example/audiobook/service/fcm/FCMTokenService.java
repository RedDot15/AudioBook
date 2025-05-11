package org.example.audiobook.service.fcm;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FCMTokenService {
    // Trong thực tế, bạn nên lưu tokens vào database
    private final Map<String, String> userTokens = new ConcurrentHashMap<>();

    public void saveToken(String userId, String token) {
        userTokens.put(userId, token);
    }

    public String getToken(String userId) {
        return userTokens.get(userId);
    }

    public void removeToken(String userId) {
        userTokens.remove(userId);
    }
} 