package com.parting.dippin.core.filter;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelLoggingReqInterceptor implements ChannelInterceptor {

    private static final ThreadLocal<Long> requestTimeMills = new ThreadLocal<>();
    public static final double NANOSECONDS_IN_A_SECOND = 1000000000.0;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        // 요청 들어온 시간 저장
        requestTimeMills.set(System.nanoTime());

        // STOMP 헤더 접근
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            return message;
        }

        // TODO MEMBER_ID : client의 ip를 받아올 방법이 존재하지 않아 추후 websocket에서 로그인 기능이 구현되고나면
        // MEMBER_ID로 가져오는 것은 어떨까 싶습니다.
        String logMessage = """
                |
                |[REQUEST] %s %s
                |>> MEMBER_ID: %s
                |>> HEADERS: %s
                |>> REQUEST_BODY: %s
                """.formatted(
                accessor.getCommand(),
                accessor.getDestination() == null ? "" : accessor.getDestination(),
                accessor.getHost() == null ? "" : accessor.getHost(),
                accessor.toNativeHeaderMap(),
                new String((byte[]) message.getPayload(), StandardCharsets.UTF_8)
        );

        log.info(logMessage);

        return message;
    }


    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            return;
        }

        long requestTime = requestTimeMills.get();
        long responseTime = System.nanoTime();
        double elapsedTime = (responseTime - requestTime) / NANOSECONDS_IN_A_SECOND;

        String logMessage = """
                |
                |[RESPONSE] %s (%.3f s)
                |>> MEMBER_ID: %s
                |>> HEADERS: %s
                |>> REQUEST_BODY: %s
                """.formatted(
                accessor.getCommand(),
                elapsedTime,
                accessor.getHost() == null ? "" : accessor.getHost(),
                accessor.toNativeHeaderMap(),
                new String((byte[]) message.getPayload(), StandardCharsets.UTF_8)
        );

        log.info(logMessage);
        requestTimeMills.remove();

        ChannelInterceptor.super.postSend(message, channel, sent);
    }
}
