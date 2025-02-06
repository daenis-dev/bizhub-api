package com.greenpalmsolutions.security.accounts.internal;

import com.greenpalmsolutions.security.accounts.api.model.RegistrationRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.*;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
final class KeycloakUser {

    @EqualsAndHashCode.Include
    private String email;
    private boolean emailVerified;
    private boolean enabled;
    @EqualsAndHashCode.Include
    private String username;
    private List<KeycloakCredentials> credentials;
    private final List<String> realmRoles = new ArrayList<>();
    private final Map<String, String[]> clientRoles = new HashMap<>();

    KeycloakUser mappedFrom(RegistrationRequest request) {
        this.email = request.getEmailAddress();
        this.emailVerified = true;
        this.enabled = true;
        this.username = request.getEmailAddress();
        this.credentials = Collections.singletonList(new KeycloakCredentials(request.getPassword(), false));
        realmRoles.add("bizhub_api_user");
        clientRoles.put("bizhub_api", new String[]{ "user" });
        return this;
    }
}
