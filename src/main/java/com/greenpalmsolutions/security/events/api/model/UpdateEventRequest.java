package com.greenpalmsolutions.security.events.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.Getter;

import java.time.ZonedDateTime;

// TODO: test
@Getter
public class UpdateEventRequest {

    private long eventId;
    private String name;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;

    public UpdateEventRequest withEventId(String eventId) {
        if (eventId == null || eventId.isEmpty()) {
            throw new InvalidRequestException("Cannot update an event without an event ID");
        }
        if (!eventId.matches("\\d*")) {
            throw new InvalidRequestException("Cannot update an event for a non numeric event ID");
        }
        this.eventId = Long.parseLong(eventId);
        return this;
    }

    public UpdateEventRequest withName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidRequestException("Name is required to update an event");
        }
        this.name = name;
        return this;
    }

    public UpdateEventRequest withStartDateTime(ZonedDateTime startDateTime) {
        if (startDateTime == null) {
            throw new InvalidRequestException("Start date time is required to update an event");
        }
        this.startDateTime = startDateTime;
        return this;
    }

    public UpdateEventRequest withEndDateTime(ZonedDateTime endDateTime) {
        if (endDateTime == null) {
            throw new InvalidRequestException("End date time is required to update an event");
        }
        this.endDateTime = endDateTime;
        return this;
    }
}
