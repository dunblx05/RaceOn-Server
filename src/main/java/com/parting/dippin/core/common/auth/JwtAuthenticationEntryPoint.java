package com.parting.dippin.core.common.auth;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.REQUIRED_LOGIN;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.core.base.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorResponse forbiddenResponse = ErrorResponse.from(REQUIRED_LOGIN);
        String json = objectMapper.writeValueAsString(forbiddenResponse);

        response.getWriter().write(json);
    }
}
