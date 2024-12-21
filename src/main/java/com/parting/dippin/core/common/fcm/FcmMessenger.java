package com.parting.dippin.core.common.fcm;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class FcmMessenger {

    @Async
    public void sendMessage(List<String> tokens, String title, String body, String imgUrl)
        throws Exception {
        List<Message> messages = new ArrayList<>();

        if (tokens.isEmpty()) {
            log.info("[FCM] EMPTY TOKEN {}, {}, {}", title, body, imgUrl);
            return;
        }

        for (String token : tokens) {
            messages.add(Message.builder()
                .setToken(token)
                .setNotification(
                    Notification.builder()
                        .setTitle(title)
                        .setImage(imgUrl)
                        .setBody(body)
                        .build()
                )
                .build());
        }


        BatchResponse response = FirebaseMessaging.getInstance().sendAll(messages);
        log.info("[FCM] SEND {} : SUCCESS {}, FAILURE {}", title, response.getSuccessCount(),
            response.getFailureCount());
    }
}
