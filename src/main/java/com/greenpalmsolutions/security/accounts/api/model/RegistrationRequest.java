package com.greenpalmsolutions.security.accounts.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import com.greenpalmsolutions.security.core.inputvalidation.EmailAddressValidator;
import lombok.Getter;

@Getter
public class RegistrationRequest {

    private String emailAddress;
    private String password;

    public RegistrationRequest withEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new InvalidRequestException("Email address is required to register for an account");
        }
        if (new EmailAddressValidator().isNotValidFormat(emailAddress)) {
            throw new InvalidRequestException("Email address must be in a valid format to register for an account");
        }
        this.emailAddress = emailAddress;
        return this;
    }

    public RegistrationRequest withPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new InvalidRequestException(
                    "Password is required to register for an account");
        }
        if (password.length() < 8) {
            throw new InvalidRequestException(
                    "Password must be at least eight characters to register for an account");
        }
        if (thereIsNotAtLeastOneLetterAndOneNumberIn(password)) {
            throw new InvalidRequestException(
                    "Password must contain at least one letter and one number to register for an account");
        }
        this.password = password;
        return this;
    }

    private boolean thereIsNotAtLeastOneLetterAndOneNumberIn(String text) {
        return !(text.matches(".*\\d.*") && text.matches(".*[A-Za-z].*"));
    }

    public RegistrationRequest confirmPassword(String confirmedPassword) {
        if (confirmedPassword == null || confirmedPassword.isEmpty() || !confirmedPassword.equals(this.password)) {
            throw new InvalidRequestException("Passwords do not match");
        }
        return this;
    }
}
