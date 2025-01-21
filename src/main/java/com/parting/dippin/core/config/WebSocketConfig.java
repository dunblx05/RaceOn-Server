package com.parting.dippin.core.config;

import com.parting.dippin.core.filter.ChannelLoggingReqInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ChannelLoggingReqInterceptor channelLoggingReqInterceptor;

    public WebSocketConfig(ChannelLoggingReqInterceptor channelLoggingReqInterceptor) {
        this.channelLoggingReqInterceptor = channelLoggingReqInterceptor;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // TODO - [설정 완료후 삭제] WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);

        registry.enableSimpleBroker("/topic", "/queue"); // 내장 메시지 브로커 사용
        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 보낼 때 사용할 prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // TODO - [설정 완료후 삭제] WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);

        // WebSocket 엔드포인트 등록
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // 클라이언트에서 들어오는 메시지 채널에 인터셉터 추가
        registration.interceptors(channelLoggingReqInterceptor);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(channelLoggingReqInterceptor);
    }
}
