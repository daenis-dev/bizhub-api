package com.greenpalmsolutions.security.accounts.internal;

import com.greenpalmsolutions.security.accounts.api.model.RegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KeycloakUserTest {

    private KeycloakUser keycloakUser;

    @BeforeEach
    void init() {
        keycloakUser = new KeycloakUser();
    }

    @Test
    void mapsFromUserRegistrationRequest() {
        String theEmailAddress = "jimmy.recard@jr.com";
        String thePassword = "changeit88";

        KeycloakUser theUpdatedUser = keycloakUser.mappedFrom(new RegistrationRequest()
                .withEmailAddress(theEmailAddress)
                .withPassword(thePassword));

        assertThat(theUpdatedUser.getEmail()).isEqualTo(theEmailAddress);
        assertThat(theUpdatedUser.isEmailVerified()).isTrue();
        assertThat(theUpdatedUser.isEnabled()).isTrue();
        assertThat(theUpdatedUser.getUsername()).isEqualTo(theEmailAddress);
        assertThat(theUpdatedUser.getCredentials().get(0).getValue()).isEqualTo(thePassword);
        assertThat(theUpdatedUser.getCredentials().get(0).isTemporary()).isFalse();
    }
}