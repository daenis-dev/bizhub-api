package com.greenpalmsolutions.security.schedulekeys.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import com.greenpalmsolutions.security.core.inputvalidation.EmailAddressValidator;
import lombok.Getter;

// TODO: test
@Getter
public class FindScheduleKeyRequest {

    private String username;

    public FindScheduleKeyRequest withUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new InvalidRequestException("Cannot find schedule key without username");
        }
        if (new EmailAddressValidator().isNotValidFormat(username)) {
            throw new InvalidRequestException("Username must be in a valid format to find schedule key");
        }
        this.username = username;
        return this;
    }
}
