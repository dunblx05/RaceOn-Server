package com.parting.dippin.core.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class ReqResLoggingFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        // INPUT UNIQUE REQUEST ID FOR LOGGING
        MDC.put("request_id", UUID.randomUUID().toString());

        // CHECK API ELAPSED TIME
        Long startTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        Long endTime = System.currentTimeMillis();

        // LOGGING API RESULT
        this.log(requestWrapper, responseWrapper, (endTime - startTime) / 1000.0);

        // RESPONSE API
        responseWrapper.copyBodyToResponse();

        // DELETE UNIQUE REQUEST ID
        MDC.clear();
    }

    private void log(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response,
            Double elapsedTime
    ) {
        try {
            String httpMethod = request.getMethod();
            String requestUri = request.getRequestURI();
            HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
            String clientIp = request.getRemoteAddr();
            String headers = Collections.list(request.getHeaderNames()).stream()
                    .map(name -> name + ": " + request.getHeader(name))
                    .collect(Collectors.joining(", "));
            String requestParam = request.getQueryString();
            String requestBody = new String(request.getContentAsByteArray());
            String responseBody = new String(response.getContentAsByteArray());

            String logMessage = """
                    |
                    |[REQUEST] %s %s %s (%.3f s)
                    |>> CLIENT_IP: %s
                    |>> HEADERS: %s
                    |>> REQUEST_PARAM: %s
                    |>> REQUEST_BODY: %s
                    |>> RESPONSE_BODY: %s
                    """.formatted(
                    httpMethod,
                    requestUri,
                    httpStatus,
                    elapsedTime,
                    clientIp,
                    headers,
                    requestParam != null ? requestParam : "N/A",
                    requestBody.isEmpty() ? "N/A" : requestBody,
                    responseBody.isEmpty() ? "N/A" : responseBody
            );

            log.info(logMessage);
        } catch (Exception e) {
            log.error("error");
        }
    }
}
