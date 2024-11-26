package com.parting.dippin.core.config;

import com.parting.dippin.core.common.auth.TokenProvider;
import com.parting.dippin.core.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;

    @Override
    public void configure(final HttpSecurity builder) {
        final JwtFilter filter = new JwtFilter(tokenProvider);

        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
