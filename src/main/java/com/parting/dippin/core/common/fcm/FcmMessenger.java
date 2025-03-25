package com.parting.dippin.core.common.fcm;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.Notification;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class FcmMessenger {

    private static final long ONE_WEEK = (long) 60 * 60 * 24 * 7;
    private Executor callBackTaskExecutor = null;

    @Async
    public void sendMessage(List<String> tokens, String title, String message,
        Map<String, String> putData)
        throws Exception {
        if (this.callBackTaskExecutor == null) {
            callBackTaskExecutor = this.callBackTaskExecutor();
        }

        List<Message> messages = new ArrayList<>();

        if (tokens.isEmpty()) {
            log.info("[FCM] EMPTY TOKEN {}, {}", title, message);
            return;
        }

        putData.put("title", title);
        putData.put("message", message);

        for (String token : tokens) {

            Message aosMessage = Message.builder()
                    .setToken(token)
                .putAllData(putData)
                .setAndroidConfig(AndroidConfig.builder().setTtl(ONE_WEEK).setNotification(
                    AndroidNotification.builder().build()).build())
                    .build();

            Message iosMessage = Message.builder()
                .setToken(token)
                .setNotification(
                    Notification.builder()
                        .setTitle(title)
                        .setBody(message)
                        .build()
                )
                .putAllData(putData)
                .setApnsConfig(ApnsConfig.builder()
                    .setAps(Aps.builder()
                        .build()).putHeader("apns-expiration", String.valueOf(System.currentTimeMillis() / 1000 + ONE_WEEK))
                    .build())
                .build();

            messages.add(aosMessage);
            messages.add(iosMessage);
        }

        for (Message msg : messages) {
            ApiFuture<String> apiFuture = FirebaseMessaging.getInstance().sendAsync(msg);

            apiFuture.addListener(() -> {
                try {
                    String response = apiFuture.get();

                    log.info("[FCM] SEND {} : SUCCESS", title);
                    log.info("FCM Notification Sent Successfully. Message ID: [{}]", response);
                    log.info("Current Call Back Thread Name: [{}]",
                        Thread.currentThread().getName());
                } catch (InterruptedException | ExecutionException e) {
                    log.error("[FCM] SEND {} : FAILURE", title);
                    log.error("FCM Notification Sent Failed. Error: [{}]", e.getMessage());
                    log.error("Current Call Back Thread Name: [{}]",
                        Thread.currentThread().getName());
                }
            }, this.callBackTaskExecutor());
        }
    }

    private Executor callBackTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setThreadNamePrefix("Fcm-Call-Back-Thread: ");
        executor.initialize();
        return executor;
    }
}
