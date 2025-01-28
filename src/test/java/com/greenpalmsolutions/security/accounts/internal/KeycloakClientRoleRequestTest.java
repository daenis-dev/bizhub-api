package com.greenpalmsolutions.security.accounts.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KeycloakClientRoleRequestTest {

    private KeycloakClientRoleRequest keycloakClientRoleRequest;

    @BeforeEach
    void init() {
        keycloakClientRoleRequest = new KeycloakClientRoleRequest();
    }

    @Test
    void setsTheId() {
        KeycloakClientRoleRequest theRequestReference = keycloakClientRoleRequest.withId("111-111");

        assertThat(theRequestReference.getId()).isEqualTo("111-111");
    }

    @Test
    void setsTheRoleName() {
        KeycloakClientRoleRequest theRequestReference = keycloakClientRoleRequest.withRoleName("user");

        assertThat(theRequestReference.getName()).isEqualTo("user");
    }
}