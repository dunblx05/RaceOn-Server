package com.parting.dippin.core.common.fcm;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class FcmMessenger {

    private static final long ONE_WEEK = (long) 60 * 60 * 24 * 7;
    private static final long EXPIRED_TIME_FOR_UNIX = new Date(
        new Date().getTime() + ONE_WEEK).getTime();

    @Async
    public void sendMessage(List<String> tokens, String title, String message, Map<String, String> putData)
        throws Exception {
        List<Message> messages = new ArrayList<>();

        if (tokens.isEmpty()) {
            log.info("[FCM] EMPTY TOKEN {}, {}", title, message);
            return;
        }

        for (String token : tokens) {
            messages.add(Message.builder()
                .setToken(token)
                .setNotification(
                    Notification.builder()
                        .setTitle(title)
                        .setBody(message)
                        .build()
                )
                .putAllData(putData)
                .setAndroidConfig(AndroidConfig.builder().setTtl(ONE_WEEK).setNotification(
                    AndroidNotification.builder().build()).build())
                .setApnsConfig(ApnsConfig.builder()
                    .setAps(Aps.builder()
                        .build()).putHeader("apns-expiration", Long.toString(EXPIRED_TIME_FOR_UNIX))
                    .build())
                .build());
        }

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendAll(messages);
            log.info("[FCM] SEND {} : SUCCESS {}, FAILURE {}", title, response.getSuccessCount(),
                response.getFailureCount());
        } catch (FirebaseMessagingException e) {
            log.error("[FCM] SEND {} : FAILURE {}", title, e.getMessage());
        }

    }
}
