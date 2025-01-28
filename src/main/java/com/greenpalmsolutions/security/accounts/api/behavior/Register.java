package com.greenpalmsolutions.security.accounts.api.behavior;

import com.greenpalmsolutions.security.accounts.api.model.RegistrationRequest;

public interface Register {

    void registerAccountForRequest(RegistrationRequest request);
}
