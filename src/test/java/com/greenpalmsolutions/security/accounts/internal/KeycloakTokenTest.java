package com.greenpalmsolutions.security.accounts.internal;

import static org.junit.jupiter.api.Assertions.*;

import com.greenpalmsolutions.security.accounts.api.model.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KeycloakTokenTest {

    private KeycloakToken keycloakToken;

    @BeforeEach
    void init() {
        keycloakToken = new KeycloakToken();
    }

    @Test
    void mapsToLoginResponse() {
        String accessToken = "xyz";
        keycloakToken.setAccessToken(accessToken);

        LoginResponse theResponse = keycloakToken.toLoginResponseFromJwtUri("");

        assertThat(theResponse.getAccessToken()).isEqualTo("Bearer xyz");
    }

    @Test
    void doesNotMapToLoginResponseForNullToken() {
        keycloakToken.setAccessToken(null);

        RuntimeException theException = assertThrows(RuntimeException.class, () -> keycloakToken.toLoginResponseFromJwtUri(""));

        assertThat(theException.getMessage()).isEqualTo("Did not receive an access token from the authorization server");
    }

    @Test
    void doesNotMapToLoginResponseForEmptyToken() {
        keycloakToken.setAccessToken("");

        RuntimeException theException = assertThrows(RuntimeException.class, () -> keycloakToken.toLoginResponseFromJwtUri(""));

        assertThat(theException.getMessage()).isEqualTo("Did not receive an access token from the authorization server");
    }
}