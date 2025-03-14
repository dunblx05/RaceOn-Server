package com.parting.dippin.core.exception;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.INTERNAL_SERVER_ERROR;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.core.base.BaseSocketRequest;
import com.parting.dippin.core.base.BaseSocketResponse;
import com.parting.dippin.core.common.CurrentTimeProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
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

    public static final String WEB_SOCKET_EXCEPTION_HANDLER = "WebSocketExceptionHandler";
    public static final String SIMP_SESSION_ID = "simpSessionId";
    public static final String STOMP_COMMAND = "stompCommand";
    public static final String SIMP_DESTINATION = "simpDestination";
    private final ObjectMapper objectMapper;
    private final CurrentTimeProvider currentTimeProvider;

    private static final String ERROR_LOG_FORMAT =
            """
                    {
                    "traceId":"{},"
                     "code":"{},"
                     "message":"{},"
                     "detailMessage":"{},"
                     "time":"{},"
                     "level":"{},"
                     "sessionId":"{},"
                     "command":"{},"
                     "destination":"{},"
                     "stackTrace":
                    {},
                    }""";

    @MessageExceptionHandler(CommonException.class)
    @SendToUser("/queue/errors")
    public BaseSocketResponse<Void> resolveCommonException(CommonException e, Message<?> message) throws IOException {
        BaseSocketRequest<?> requestDto = convertToRequestDto(message);
        writeLog(e.getCodeAndMessage(), e, message);

        return BaseSocketResponse.error(e.getCodeAndMessage(), requestDto.getCommand());
    }

    @MessageExceptionHandler(BusinessException.class)
    @SendToUser("/queue/errors")
    public BaseSocketResponse<Void> resolveBusinessException(BusinessException e, Message<?> message)
            throws IOException {
        BaseSocketRequest<?> requestDto = convertToRequestDto(message);
        writeLog(e.getCodeAndMessage(), e, message);

        return BaseSocketResponse.error(e.getCodeAndMessage(), requestDto.getCommand());
    }

    @MessageExceptionHandler(Exception.class)
    @SendToUser("/queue/errors")
    public BaseSocketResponse<Void> resolveException(Exception e, Message<?> message) throws IOException {
        BaseSocketRequest<?> requestDto = convertToRequestDto(message);
        writeLog(INTERNAL_SERVER_ERROR, e, message);

        return BaseSocketResponse.error(INTERNAL_SERVER_ERROR, requestDto.getCommand());
    }

    private BaseSocketRequest<?> convertToRequestDto(Message<?> message) throws IOException {
        byte[] payload = (byte[]) message.getPayload();

        return objectMapper.readValue(payload, BaseSocketRequest.class);
    }

    private void writeLog(
            final CodeAndMessage codeAndMessage,
            final Exception exception,
            final Message<?> request
    ) {
        final String longId = UUID.randomUUID().toString();
        final String code = codeAndMessage.code();
        final String message = codeAndMessage.message();
        final String detailMessage = exception.getMessage();
        final LocalDateTime time = currentTimeProvider.now();
        final String sessionId = (String) request.getHeaders().get(SIMP_SESSION_ID);
        final String command = Objects.requireNonNullElse(request.getHeaders().get(STOMP_COMMAND), "").toString();
        final String destination = (String) request.getHeaders().get(SIMP_DESTINATION);

        String[] stackTraces = Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .toArray(String[]::new);
        final String stackTrace = String.join("\n", stackTraces);

        log.error(
                ERROR_LOG_FORMAT,
                longId,
                code,
                message,
                detailMessage,
                time,
                WEB_SOCKET_EXCEPTION_HANDLER,
                sessionId,
                command,
                destination,
                stackTrace
        );
    }
}
