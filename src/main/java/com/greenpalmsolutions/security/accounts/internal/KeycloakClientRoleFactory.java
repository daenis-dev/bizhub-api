package com.greenpalmsolutions.security.accounts.internal;

import org.springframework.stereotype.Component;

@Component
class KeycloakClientRoleFactory {

    KeycloakClientRoleRequest forRole(String roleName) {
        if ("user".equalsIgnoreCase(roleName)) {
            return new KeycloakClientRoleRequest()
                    .withRoleName("user")
                    .withId("d8f304ac-82df-4c2b-a7cc-ee993b8caddd");
        }
        throw new RuntimeException("Cannot add unsupported role for user");
    }
}
