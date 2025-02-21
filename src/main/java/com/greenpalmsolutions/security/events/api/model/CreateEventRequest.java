package com.greenpalmsolutions.security.events.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class CreateEventRequest {

    private String name;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;

    public CreateEventRequest withName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidRequestException("Name is required to create an event");
        }
        this.name = name;
        return this;
    }

    public CreateEventRequest withStartDateTime(ZonedDateTime startDateTime) {
        if (startDateTime == null) {
            throw new InvalidRequestException("Start date time is required to create an event");
        }
        this.startDateTime = startDateTime;
        return this;
    }

    public CreateEventRequest withEndDateTime(ZonedDateTime endDateTime) {
        if (endDateTime == null) {
            throw new InvalidRequestException("End date time is required to create an event");
        }
        this.endDateTime = endDateTime;
        return this;
    }
}
