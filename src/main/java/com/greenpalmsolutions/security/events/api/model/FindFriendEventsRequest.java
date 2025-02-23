package com.greenpalmsolutions.security.events.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import com.greenpalmsolutions.security.core.inputvalidation.EmailAddressValidator;
import lombok.Getter;

@Getter
public class FindFriendEventsRequest {

    private String username;

    public FindFriendEventsRequest withUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new InvalidRequestException("Username is required to find friend events");
        }
        if (new EmailAddressValidator().isNotValidFormat(username)) {
            throw new InvalidRequestException("Username must be in a valid format to find friend events");
        }
        this.username = username;
        return this;
    }
}
