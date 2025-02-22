package com.greenpalmsolutions.security.accounts.internal;

import com.greenpalmsolutions.security.accounts.api.model.LoginRequest;
import com.greenpalmsolutions.security.accounts.api.model.LoginResponse;
import com.greenpalmsolutions.security.accounts.api.model.RegistrationRequest;
import com.greenpalmsolutions.security.accounts.api.model.ResetPasswordRequest;

public interface AuthorizationServer {

    void sendARegistrationRequestToAuthorizationServerForThe(RegistrationRequest request);

    LoginResponse getAccessTokenFromTheAuthorizationServerForThe(LoginRequest request);

    void sendEmailToResetPasswordForRequest(ResetPasswordRequest request);

    String findUserIdForUsername(String username);
}
