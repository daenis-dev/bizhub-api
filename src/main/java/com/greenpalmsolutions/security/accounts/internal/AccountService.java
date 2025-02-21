package com.greenpalmsolutions.security.accounts.internal;

import com.greenpalmsolutions.security.accounts.api.behavior.*;
import com.greenpalmsolutions.security.accounts.api.model.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AccountService implements Login, Register, ResetPassword, FindCurrentAccount, FindUserIdForUsername {

    private final KeycloakConnection keycloakConnection;

    @Override
    public LoginResponse loginForRequest(LoginRequest request) {
        return keycloakConnection.getAccessTokenFromTheAuthorizationServerForThe(request);
    }

    @Override
    public void registerAccountForRequest(RegistrationRequest request) {
        keycloakConnection.sendARegistrationRequestToAuthorizationServerForThe(request);
    }

    @Override
    public void sendEmailToResetPasswordForRequest(ResetPasswordRequest request) {
        keycloakConnection.sendEmailToResetPasswordForRequest(request);
    }

    @Override
    public String getUserIdForCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new RuntimeException("An error occurred while getting the current user");
        }

        return jwt.getSubject();
    }

    @Override
    public String findForUsername(String username) {
        return keycloakConnection.findUserIdForUsername(username);
    }
}
