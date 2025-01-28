package com.greenpalmsolutions.security.accounts.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KeycloakClientRoleFactoryTest {

    private KeycloakClientRoleFactory keycloakClientRoleFactory;

    @BeforeEach
    void init() {
        keycloakClientRoleFactory = new KeycloakClientRoleFactory();
    }

    @Test
    void createsARequestForTheUserRole() {
        KeycloakClientRoleRequest theCreatedRequest = keycloakClientRoleFactory.forRole("user");

        assertThat(theCreatedRequest.getName()).isEqualTo("user");
        assertThat(theCreatedRequest.getId()).isEqualTo("d8f304ac-82df-4c2b-a7cc-ee993b8caddd");
    }

    @Test
    void doesNotCreateARequestForAnyOtherRole() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> keycloakClientRoleFactory.forRole("invalid"));

        assertThat(thrown.getMessage()).isEqualTo("Cannot add unsupported role for user");
    }
}