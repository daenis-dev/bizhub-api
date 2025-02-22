package com.greenpalmsolutions.security.accounts.internal;

import com.greenpalmsolutions.security.accounts.api.model.*;
import org.keycloak.admin.client.Keycloak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
class KeycloakConnection {

    private final Logger LOGGER = LoggerFactory.getLogger(KeycloakConnection.class);

    @Value("${keycloak.base-url}")
    private String KEYCLOAK_BASE_URL;

    @Value("${keycloak.admin.realm}")
    private String KEYCLOAK_ADMIN_REALM;

    @Value("${keycloak.admin.username}")
    private String KEYCLOAK_ADMIN_USERNAME;

    @Value("${keycloak.admin.password}")
    private String KEYCLOAK_ADMIN_PASSWORD;

    @Value("${keycloak.admin.client-name}")
    private String KEYCLOAK_ADMIN_CLIENT_NAME;

    @Value("${keycloak.bizhub.base-url}")
    private String KEYCLOAK_BIZHUB_BASE_URL;

    @Value("${keycloak.bizhub.users-api-url}")
    private String KEYCLOAK_BIZHUB_USERS_API;

    @Value("${keycloak.bizhub.token-schema}")
    private String KEYCLOAK_BIZHUB_TOKEN_SCHEMA;

    @Value("${keycloak.bizhub.login-url}")
    private String KEYCLOAK_BIZHUB_LOGIN_URL;

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String KEYCLOAK_BIZHUB_JWT_URI;

    @Value("${keycloak.bizhub.uuid}")
    private String KEYCLOAK_BIZHUB_UUID;

    @Value("${jwt.auth.converter.resource-id}")
    private String KEYCLOAK_BIZHUB_RESOURCE_ID;

    private final KeycloakClientRoleFactory keycloakClientRoleFactory;

    KeycloakConnection(KeycloakClientRoleFactory keycloakClientRoleFactory) {
        this.keycloakClientRoleFactory = keycloakClientRoleFactory;
    }

    void sendARegistrationRequestToAuthorizationServerForThe(RegistrationRequest request) {
        try {
            registeringTheUser(adminWebClient(), request);
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while registering the user", ex);
        }
    }

    private WebClient adminWebClient() {
        Keycloak keycloak = Keycloak.getInstance(KEYCLOAK_BASE_URL, KEYCLOAK_ADMIN_REALM, KEYCLOAK_ADMIN_USERNAME, KEYCLOAK_ADMIN_PASSWORD, KEYCLOAK_ADMIN_CLIENT_NAME);
        String accessToken = KEYCLOAK_BIZHUB_TOKEN_SCHEMA + " " + keycloak.tokenManager().getAccessTokenString();
        return WebClient
                .builder()
                .baseUrl(KEYCLOAK_BIZHUB_BASE_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.ACCEPT, ALL_VALUE);
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, accessToken);
                })
                .build();
    }

    private void registeringTheUser(WebClient webClient, RegistrationRequest request) {
        createTheUserWithoutAnyRoles(webClient, request);
        addRoleToUser(webClient, request);
    }

    private void createTheUserWithoutAnyRoles(WebClient webClient, RegistrationRequest request) {
        KeycloakResponse keycloakResponse = webClient
                .post()
                .uri(KEYCLOAK_BIZHUB_USERS_API)
                .contentType(APPLICATION_JSON)
                .bodyValue(new KeycloakUser().mappedFrom(request))
                .exchangeToMono(response -> response.bodyToMono(KeycloakResponse.class))
                .block(Duration.ofSeconds(30));
        if (keycloakResponse != null && keycloakResponse.getError() != null && !keycloakResponse.getError().isEmpty()) {
            throw new RuntimeException("An error occurred while registering the user with message from Keycloak: " + keycloakResponse.getError());
        }
    }

    private void addRoleToUser(WebClient webClient, RegistrationRequest request) {
        KeycloakResponse keycloakRoleResponse = webClient
                .post()
                .uri(KEYCLOAK_BIZHUB_USERS_API + "/" + getUserDetails(webClient, request).getId() + "/role-mappings/clients/" + "8b18673e-fdde-4da8-8835-fb9acf2be047")
                .contentType(APPLICATION_JSON)
                .bodyValue(Arrays.asList(keycloakClientRoleFactory.forRole("user")))
                .exchangeToMono(response -> response.bodyToMono(KeycloakResponse.class))
                .block(Duration.ofSeconds(30));
        if (keycloakRoleResponse != null && keycloakRoleResponse.getError() != null && !keycloakRoleResponse.getError().isEmpty()) {
            throw new RuntimeException("An error occurred while registering the user with message from Keycloak: " + keycloakRoleResponse.getError());
        }
    }

    private KeycloakUserDetails getUserDetails(WebClient webClient, RegistrationRequest request) {
        KeycloakUserDetails userDetails = webClient
                .get()
                .uri(KEYCLOAK_BIZHUB_USERS_API + "?username=" + request.getEmailAddress())
                .exchangeToMono(response -> response.bodyToMono(KeycloakUserDetails[].class))
                .block(Duration.ofSeconds(30))[0];
        if (userDetails == null || userDetails.getId() == null || userDetails.getId().equalsIgnoreCase("")) {
            throw new RuntimeException("An error occurred while registering the user");
        }
        return userDetails;
    }

    LoginResponse getAccessTokenFromTheAuthorizationServerForThe(LoginRequest request) {
        return theTokenFor(request).toLoginResponseFromJwtUri(KEYCLOAK_BIZHUB_JWT_URI);
    }

    private KeycloakToken theTokenFor(LoginRequest theRequest) {
        return Optional.of(Objects.requireNonNull(
                        userWebClient()
                                .post()
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .body(BodyInserters
                                        .fromFormData("username", theRequest.getEmailAddress())
                                        .with("password", theRequest.getPassword())
                                        .with("client_id", KEYCLOAK_BIZHUB_RESOURCE_ID)
                                        .with("grant_type", "password"))
                                .exchangeToMono(response -> response.bodyToMono(KeycloakToken.class))
                                .block(Duration.ofSeconds(30))))
                .orElseThrow(() -> new RuntimeException("Error occurred while logging into authorization server"));
    }

    private WebClient userWebClient() {
        return WebClient
                .builder()
                .baseUrl(KEYCLOAK_BIZHUB_LOGIN_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.ACCEPT, ALL_VALUE);
                })
                .build();
    }

    void sendEmailToResetPasswordForRequest(ResetPasswordRequest request) {
        Keycloak keycloak = Keycloak.getInstance(KEYCLOAK_BASE_URL, KEYCLOAK_ADMIN_REALM, KEYCLOAK_ADMIN_USERNAME, KEYCLOAK_ADMIN_PASSWORD, KEYCLOAK_ADMIN_CLIENT_NAME);
        String accessToken = KEYCLOAK_BIZHUB_TOKEN_SCHEMA + " " + keycloak.tokenManager().getAccessTokenString();

        WebClient webClient = WebClient
                .builder()
                .baseUrl(KEYCLOAK_BIZHUB_BASE_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.ACCEPT, ALL_VALUE);
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, accessToken);
                })
                .build();

        List<LinkedHashMap> keycloakUserDetails = webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/users").queryParam("username", request.getEmailAddress()).build())
                .exchangeToMono(response -> response.bodyToMono(List.class))
                .block(Duration.ofSeconds(30));

        if (keycloakUserDetails == null || keycloakUserDetails.isEmpty()) {
            throw new RuntimeException("Cannot retrieve user details because they do not exist");
        }

        String userId = String.valueOf(keycloakUserDetails.get(0).get("id"));

        webClient
                .put()
                .uri(uriBuilder -> uriBuilder.path("/users/" + userId + "/execute-actions-email")
                        .queryParam("client_id", KEYCLOAK_ADMIN_CLIENT_NAME)
                        .build())
                .bodyValue(List.of("UPDATE_PASSWORD"))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response ->
                        response.bodyToMono(String.class).flatMap(errorBody -> Mono.error(new RuntimeException("Failed to send password reset email with message: " + errorBody)))
                )
                .bodyToMono(Void.class)
                .block(Duration.ofSeconds(30));
    }

    String findUserIdForUsername(String username) {
        Keycloak keycloak = Keycloak.getInstance(KEYCLOAK_BASE_URL, KEYCLOAK_ADMIN_REALM, KEYCLOAK_ADMIN_USERNAME, KEYCLOAK_ADMIN_PASSWORD, KEYCLOAK_ADMIN_CLIENT_NAME);
        String accessToken = KEYCLOAK_BIZHUB_TOKEN_SCHEMA + " " + keycloak.tokenManager().getAccessTokenString();

        WebClient webClient = WebClient
                .builder()
                .baseUrl(KEYCLOAK_BIZHUB_BASE_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.ACCEPT, ALL_VALUE);
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, accessToken);
                })
                .build();

        List<LinkedHashMap> keycloakUserDetails = webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/users").queryParam("username", username).build())
                .exchangeToMono(response -> response.bodyToMono(List.class))
                .block(Duration.ofSeconds(30));

        if (keycloakUserDetails == null || keycloakUserDetails.isEmpty()) {
            throw new RuntimeException("Cannot retrieve user details because they do not exist");
        }

        return String.valueOf(keycloakUserDetails.get(0).get("id"));
    }
}
