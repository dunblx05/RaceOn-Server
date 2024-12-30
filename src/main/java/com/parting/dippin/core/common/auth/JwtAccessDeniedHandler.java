package com.parting.dippin.core.common.auth;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.UN_AUTHORIZED;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.core.base.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        ErrorResponse forbiddenResponse = ErrorResponse.from(UN_AUTHORIZED);
        String json = objectMapper.writeValueAsString(forbiddenResponse);

        response.getWriter().write(json);
    }
}