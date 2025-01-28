package com.greenpalmsolutions.security.accounts.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenpalmsolutions.security.accounts.api.model.LoginResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
final class KeycloakToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonIgnore
    LoginResponse toLoginResponseFromJwtUri(String jwtUri) {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new RuntimeException("Did not receive an access token from the authorization server");
        }
        return new LoginResponse("Bearer ".concat(accessToken), 1000 * 60 * 60 * 24, jwtUri);
    }
}
