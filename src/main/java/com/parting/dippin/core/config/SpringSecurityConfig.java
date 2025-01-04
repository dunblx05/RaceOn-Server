package com.parting.dippin.core.config;

import com.parting.dippin.core.common.auth.JwtAccessDeniedHandler;
import com.parting.dippin.core.common.auth.JwtAuthenticationEntryPoint;
import com.parting.dippin.core.common.auth.TokenProvider;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            return config;
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // HTTP BASIC POLICY
        http.httpBasic(HttpBasicConfigurer::disable);

        // CSRF POLICY
        http.csrf(AbstractHttpConfigurer::disable);

        // CORS POLICY
        http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()));

        // AUTHORIZE POLICY
        http.authorizeHttpRequests(
                request -> request
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/docs/**").permitAll()
                        .requestMatchers("/hello").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/kakao/callback").permitAll()
                        .requestMatchers("/apple/callback").permitAll()
                        .anyRequest().authenticated());

        // SESSION POLICY
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // JWT POLICY
        http.with(new JwtSecurityConfig(tokenProvider), customizer -> {});

        // EXCEPTION HANDLING POLICY
        http.exceptionHandling(handler -> handler
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler));

        return http.build();
    }
}
