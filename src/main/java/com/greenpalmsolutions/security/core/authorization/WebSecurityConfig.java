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
    private final String DEMO_USER = "demo_user";

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests
                            .requestMatchers(HttpMethod.GET, "/v1/schedule").permitAll()
                            .requestMatchers(HttpMethod.POST, "/v1/register").permitAll()
                            .requestMatchers(HttpMethod.POST, "/v1/login").permitAll()
                            .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                            .requestMatchers(HttpMethod.POST, "/v1/reset-password").permitAll()
                            .requestMatchers(HttpMethod.POST, "/v1/logout").hasAnyRole(USER, DEMO_USER)
                            .requestMatchers(HttpMethod.POST, "/v1/backups").hasRole(USER)
                            .requestMatchers(HttpMethod.GET, "/v1/backups").hasAnyRole(USER, DEMO_USER)
                            .requestMatchers(HttpMethod.GET, "/v1/backups/**").hasAnyRole(USER, DEMO_USER)
                            .requestMatchers(HttpMethod.POST, "/v1/events").hasRole(USER)
                            .requestMatchers(HttpMethod.GET, "/v1/events").hasAnyRole(USER, DEMO_USER)
                            .requestMatchers(HttpMethod.GET, "/v1/event-date-times").hasRole(USER)
                            .requestMatchers(HttpMethod.PUT, "/v1/events/**").hasRole(USER)
                            .requestMatchers(HttpMethod.DELETE, "/v1/events/**").hasRole(USER)
                            .requestMatchers(HttpMethod.POST, "/v1/schedule-keys").hasRole(USER)
                            .requestMatchers(HttpMethod.GET, "/v1/schedule-keys").hasRole(USER)
                            .requestMatchers(HttpMethod.DELETE, "/v1/schedule-keys").hasRole(USER)
                            .anyRequest().authenticated();
                })
                .csrf(configurer -> {
                            configurer.ignoringRequestMatchers("/v1/register");
                            configurer.ignoringRequestMatchers("/v1/login");
                            configurer.ignoringRequestMatchers("/v1/logout");
                            configurer.ignoringRequestMatchers("/v1/reset-password");
                            configurer.ignoringRequestMatchers("/v1/backups");
                            configurer.ignoringRequestMatchers("/v1/events");
                            configurer.ignoringRequestMatchers("/v1/events/**");
                            configurer.ignoringRequestMatchers("/v1/schedule-keys");
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
        corsConfiguration.setAllowedOrigins(
                Arrays.asList(
                        "http://localhost:4200",
                        "https://bizhub.greenpalm-solutions.com",
                        "https://www.bizhub.greenpalm-solutions.com"
                )
        );
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}