package com.greenpalmsolutions.security.core.inputvalidation;

public class EmailAddressValidator {

    private final String FORMAT = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public boolean isNotValidFormat(String emailAddress) {
        return emailAddress == null || !emailAddress.matches(FORMAT);
    }
}
