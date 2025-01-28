package com.greenpalmsolutions.security.accounts.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
final class KeycloakCredentials {

    private String value;
    private boolean temporary;
}
