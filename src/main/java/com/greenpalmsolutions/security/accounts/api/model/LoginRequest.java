package com.greenpalmsolutions.security.accounts.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import com.greenpalmsolutions.security.core.inputvalidation.EmailAddressValidator;
import lombok.Getter;

@Getter
public class LoginRequest {

    private String emailAddress;
    private String password;

    public LoginRequest withEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new InvalidRequestException("Email address is required to log in");
        }
        if (new EmailAddressValidator().isNotValidFormat(emailAddress)) {
            throw new InvalidRequestException("Email address must be in a valid format to log in");
        }
        this.emailAddress = emailAddress;
        return this;
    }

    public LoginRequest withPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new InvalidRequestException("Password is required to log in");
        }
        this.password = password;
        return this;
    }
}
