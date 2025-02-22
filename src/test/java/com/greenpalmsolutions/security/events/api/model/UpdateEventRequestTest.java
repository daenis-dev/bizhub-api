package com.greenpalmsolutions.security.events.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UpdateEventRequestTest {

    private UpdateEventRequest updateEventRequest;

    @BeforeEach
    void init() {
        updateEventRequest = new UpdateEventRequest();
    }

    @Test
    void setsEventIdForValidInput() {
        String theId = "2222";

        UpdateEventRequest theUpdatedRequest = updateEventRequest.withEventId(theId);

        Long theIdFromTheUpdatedRequest = theUpdatedRequest.getEventId();
        assertThat(theIdFromTheUpdatedRequest).isEqualTo(Long.parseLong(theId));
    }

    @Test
    void doesNotSetTheEventIdForNullInput() {
        String thePredictedMessage = "Cannot update an event without an event ID";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class, () -> updateEventRequest.withEventId(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheEventIdForEmptyInput() {
        String thePredictedMessage = "Cannot update an event without an event ID";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class, () -> updateEventRequest.withEventId(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheEventIdForNonNumericData() {
        String thePredictedMessage = "Cannot update an event for a non numeric event ID";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class, () -> updateEventRequest.withEventId("dd33"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsNameForValidInput() {
        String theName = "My Meeting";

        UpdateEventRequest theUpdatedRequest = updateEventRequest.withName(theName);

        String theNameFromTheUpdatedRequest = theUpdatedRequest.getName();
        assertThat(theNameFromTheUpdatedRequest).isEqualTo(theName);
    }

    @Test
    void doesNotSetTheNameForNullInput() {
        String thePredictedMessage = "Name is required to update an event";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> updateEventRequest.withName(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheNameForEmptyInput() {
        String thePredictedMessage = "Name is required to update an event";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> updateEventRequest.withName(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsStartDateTimeForValidInput() {
        ZonedDateTime theStartDateTime = ZonedDateTime.now();

        UpdateEventRequest theUpdatedRequest = updateEventRequest.withStartDateTime(theStartDateTime);

        ZonedDateTime theStartDateTimeFromTheUpdatedRequest = theUpdatedRequest.getStartDateTime();
        assertThat(theStartDateTimeFromTheUpdatedRequest).isEqualTo(theStartDateTime);
    }

    @Test
    void doesNotSetTheStartDateTimeForNullInput() {
        String thePredictedMessage = "Start date time is required to update an event";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> updateEventRequest.withStartDateTime(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsEndDateTimeForValidInput() {
        ZonedDateTime theEndDateTime = ZonedDateTime.now();

        UpdateEventRequest theUpdatedRequest = updateEventRequest.withEndDateTime(theEndDateTime);

        ZonedDateTime theEndDateTimeFromTheUpdatedRequest = theUpdatedRequest.getEndDateTime();
        assertThat(theEndDateTimeFromTheUpdatedRequest).isEqualTo(theEndDateTime);
    }

    @Test
    void doesNotSetTheEndDateTimeForNullInput() {
        String thePredictedMessage = "End date time is required to update an event";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> updateEventRequest.withEndDateTime(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}