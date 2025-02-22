package com.greenpalmsolutions.security.accounts.internal;

import com.greenpalmsolutions.security.accounts.api.model.LoginRequest;
import com.greenpalmsolutions.security.accounts.api.model.LoginResponse;
import com.greenpalmsolutions.security.accounts.api.model.RegistrationRequest;
import com.greenpalmsolutions.security.accounts.api.model.ResetPasswordRequest;
import org.keycloak.admin.client.Keycloak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Profile("it")
@Component
class LocalAuthorizationServer implements AuthorizationServer {

    @Override
    public void sendARegistrationRequestToAuthorizationServerForThe(RegistrationRequest request) {

    }

    @Override
    public LoginResponse getAccessTokenFromTheAuthorizationServerForThe(LoginRequest request) {
        return null;
    }

    @Override
    public void sendEmailToResetPasswordForRequest(ResetPasswordRequest request) {
    }

    @Override
    public String findUserIdForUsername(String username) {
        return username.equals("invalid") ? "333f" : "456-def";
    }
}
