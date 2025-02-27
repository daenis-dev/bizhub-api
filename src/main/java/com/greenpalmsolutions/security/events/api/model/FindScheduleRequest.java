package com.greenpalmsolutions.security.events.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import com.greenpalmsolutions.security.core.inputvalidation.EmailAddressValidator;
import lombok.Getter;

@Getter
public class FindScheduleRequest {

    private String username;
    private String scheduleKey;

    public FindScheduleRequest withUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new InvalidRequestException("Cannot find schedule without username");
        }
        if (new EmailAddressValidator().isNotValidFormat(username)) {
            throw new InvalidRequestException("Username must be in a valid format to find schedule");
        }
        this.username = username;
        return this;
    }

    public FindScheduleRequest withScheduleKey(String scheduleKey) {
        if (scheduleKey == null || scheduleKey.isEmpty()) {
            throw new InvalidRequestException("Cannot find schedule without schedule key");
        }
        this.scheduleKey = scheduleKey;
        return this;
    }
}
