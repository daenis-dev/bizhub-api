package com.greenpalmsolutions.security.events.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import com.greenpalmsolutions.security.core.inputvalidation.EmailAddressValidator;
import lombok.Getter;

@Getter
public class FindEventDateTimesRequest {

    private String username;

    public FindEventDateTimesRequest withUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new InvalidRequestException("Username is required to find event date times");
        }
        if (new EmailAddressValidator().isNotValidFormat(username)) {
            throw new InvalidRequestException("Username must be in a valid format to find event date times");
        }
        this.username = username;
        return this;
    }
}
