package com.greenpalmsolutions.security.accounts.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private final String accessToken;
    private final long millisecondsUntilTokenExpiration;
    private final String jwtUri;
}
