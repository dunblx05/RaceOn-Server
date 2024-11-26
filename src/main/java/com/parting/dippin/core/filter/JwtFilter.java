package com.parting.dippin.core.filter;

import com.parting.dippin.core.common.auth.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final String REISSUE_PATH = "/auth/reissue";

    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = tokenProvider.resolveToken(httpServletRequest);

        if (isInValidToken(request, jwt)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = tokenProvider.getAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private boolean isInValidToken(
            ServletRequest request,
            String jwt
    ) {
        return isInValidTokenForm(jwt) ||
                isInValidTokenUsage(request, jwt);
    }

    private boolean isInValidTokenForm(String jwt) {
        return !StringUtils.hasText(jwt) || tokenProvider.isInvalidToken(jwt);
    }

    private boolean isInValidTokenUsage(
            ServletRequest request,
            String jwt
    ) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        boolean isReissuePath = requestURI.equals(REISSUE_PATH);
        boolean isRefreshToken = tokenProvider.isRefreshToken(jwt);

        return isReissuePath ^ isRefreshToken;
    }
}