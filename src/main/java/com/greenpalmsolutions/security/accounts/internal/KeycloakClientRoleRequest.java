package com.greenpalmsolutions.security.accounts.internal;

import lombok.Getter;

@Getter
class KeycloakClientRoleRequest {

    private String id;
    private String name;

    KeycloakClientRoleRequest withId(String id) {
        this.id = id;
        return this;
    }

    KeycloakClientRoleRequest withRoleName(String roleName) {
        this.name = roleName;
        return this;
    }
}
