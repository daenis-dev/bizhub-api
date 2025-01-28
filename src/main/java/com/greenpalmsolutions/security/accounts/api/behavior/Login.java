package com.greenpalmsolutions.security.accounts.api.behavior;

import com.greenpalmsolutions.security.accounts.api.model.LoginRequest;
import com.greenpalmsolutions.security.accounts.api.model.LoginResponse;

public interface Login {

    LoginResponse loginForRequest(LoginRequest request);
}
