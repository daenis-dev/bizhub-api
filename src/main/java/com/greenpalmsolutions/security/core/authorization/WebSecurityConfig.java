package com.greenpalmsolutions.security.core.authorization;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebSecurityConfig {

    private final String USER = "user";

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests
                            .requestMatchers(HttpMethod.POST, "/v1/register").permitAll()
                            .requestMatchers(HttpMethod.POST, "/v1/login").permitAll()
                            .requestMatchers(HttpMethod.POST, "/v1/reset-password").permitAll()
                            .requestMatchers(HttpMethod.POST, "/v1/logout").hasRole(USER)
                            .requestMatchers(HttpMethod.POST, "/v1/backups").hasRole(USER)
                            .requestMatchers(HttpMethod.GET, "/v1/backups").hasRole(USER)
                            .requestMatchers(HttpMethod.GET, "/v1/backups/**").hasRole(USER)
                            .anyRequest().authenticated();
                })
                .csrf(configurer -> {
                            configurer.ignoringRequestMatchers("/v1/register");
                            configurer.ignoringRequestMatchers("/v1/login");
                            configurer.ignoringRequestMatchers("/v1/logout");
                            configurer.ignoringRequestMatchers("/v1/reset-password");
                            configurer.ignoringRequestMatchers("/v1/backups");
                })
                .oauth2ResourceServer(resourceServer -> {
                    resourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter));
                })
                .sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}