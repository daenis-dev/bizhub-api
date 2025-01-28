package com.greenpalmsolutions.security.accounts.api.behavior;

import com.greenpalmsolutions.security.accounts.api.model.ResetPasswordRequest;

public interface ResetPassword {

    void sendEmailToResetPasswordForRequest(ResetPasswordRequest request);
}
