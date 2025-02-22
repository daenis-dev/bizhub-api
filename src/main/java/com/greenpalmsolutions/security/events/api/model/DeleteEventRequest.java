package com.greenpalmsolutions.security.events.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.Getter;

// TODO: test
@Getter
public class DeleteEventRequest {

    private long eventId;

    public DeleteEventRequest withEventId(String eventId) {
        if (eventId == null || eventId.isEmpty()) {
            throw new InvalidRequestException("Cannot delete an event without an event ID");
        }
        if (!eventId.matches("\\d*")) {
            throw new InvalidRequestException("Cannot delete an event for a non numeric event ID");
        }
        this.eventId = Long.parseLong(eventId);
        return this;
    }
}
