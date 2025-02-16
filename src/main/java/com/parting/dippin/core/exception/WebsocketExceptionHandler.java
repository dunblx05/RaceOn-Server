package com.parting.dippin.core.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.core.base.BaseSocketRequest;
import com.parting.dippin.core.base.BaseSocketResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class WebsocketExceptionHandler {

    private final ObjectMapper objectMapper;

    @MessageExceptionHandler(CommonException.class)
    @SendToUser("/queue/errors")
    public BaseSocketResponse<Void> resolveCommonException(CommonException e, Message<?> message) throws IOException {
        BaseSocketRequest<?> requestDto = convertToRequestDto(message);

        return BaseSocketResponse.error(e.getCodeAndMessage(), requestDto.getCommand());
    }

    @MessageExceptionHandler(BusinessException.class)
    @SendToUser("/queue/errors")
    public BaseSocketResponse<Void> resolveBusinessException(BusinessException e, Message<?> message) throws IOException {
        BaseSocketRequest<?> requestDto = convertToRequestDto(message);

        return BaseSocketResponse.error(e.getCodeAndMessage(), requestDto.getCommand());
    }

    @MessageExceptionHandler(Exception.class)
    @SendToUser("/queue/errors")
    public BaseSocketResponse<Void> resolveException(Exception e, Message<?> message) throws IOException {
        BaseSocketRequest<?> requestDto = convertToRequestDto(message);

        return BaseSocketResponse.error(CommonCodeAndMessage.INTERNAL_SERVER_ERROR, requestDto.getCommand());
    }

    private BaseSocketRequest<?> convertToRequestDto(Message<?> message) throws IOException {
        byte[] payload = (byte[]) message.getPayload();

        return objectMapper.readValue(payload, BaseSocketRequest.class);
    }
}
