package com.parting.dippin.core.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.core.base.BaseResponse;
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
        BaseResponse<Void> forbiddenResponse = BaseResponse.fail(403);
        String json = objectMapper.writeValueAsString(forbiddenResponse);

        response.getWriter().write(json);
    }
}