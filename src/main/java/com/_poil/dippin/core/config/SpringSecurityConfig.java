package com._poil.dippin.core.config;

import java.util.Collections;
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
public class SpringSecurityConfig {

    CorsConfigurationSource corsConfigurationSource() {
        return ruquest -> {
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

        // CRSF POLICY
        http.csrf(AbstractHttpConfigurer::disable);

        // CORS POLICY
        http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()));

        // AUTHORIZE POLICY
        http.authorizeHttpRequests(
            request -> request.requestMatchers("/docs/*").permitAll()
                .requestMatchers("/*").permitAll()
                .anyRequest().authenticated());

        // SESSION POLICY
        http.sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // TODO - JWT FILTER POLICY

        return http.build();
    }
}
