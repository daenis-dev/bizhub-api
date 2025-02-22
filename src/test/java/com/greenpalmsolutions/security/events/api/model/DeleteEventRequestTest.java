package com.greenpalmsolutions.security.events.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DeleteEventRequestTest {

    private DeleteEventRequest deleteEventRequest;

    @BeforeEach
    void init() {
        deleteEventRequest = new DeleteEventRequest();
    }

    @Test
    void setsEventIdForValidInput() {
        String theId = "2222";

        DeleteEventRequest theUpdatedRequest = deleteEventRequest.withEventId(theId);

        Long theIdFromTheUpdatedRequest = theUpdatedRequest.getEventId();
        assertThat(theIdFromTheUpdatedRequest).isEqualTo(Long.parseLong(theId));
    }

    @Test
    void doesNotSetTheEventIdForNullInput() {
        String thePredictedMessage = "Cannot delete an event without an event ID";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class, () -> deleteEventRequest.withEventId(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheEventIdForEmptyInput() {
        String thePredictedMessage = "Cannot delete an event without an event ID";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class, () -> deleteEventRequest.withEventId(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheEventIdForNonNumericData() {
        String thePredictedMessage = "Cannot delete an event for a non numeric event ID";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class, () -> deleteEventRequest.withEventId("dd33"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}