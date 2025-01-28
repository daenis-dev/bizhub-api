package com.greenpalmsolutions.security.accounts.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import com.greenpalmsolutions.security.core.inputvalidation.EmailAddressValidator;
import lombok.Getter;

@Getter
public class ResetPasswordRequest {

    private String emailAddress;

    public ResetPasswordRequest withEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new InvalidRequestException("Email address is required to reset password");
        }
        if (new EmailAddressValidator().isNotValidFormat(emailAddress)) {
            throw new InvalidRequestException("Email address must be in a valid format to reset password");
        }
        this.emailAddress = emailAddress;
        return this;
    }
}
