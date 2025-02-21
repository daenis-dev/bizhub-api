package com.greenpalmsolutions.security.events.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CreateEventRequestTest {

    private CreateEventRequest createEventRequest;

    @BeforeEach
    void init() {
        createEventRequest = new CreateEventRequest();
    }

    @Test
    void setsNameForValidInput() {
        String theName = "My Meeting";

        CreateEventRequest theUpdatedRequest = createEventRequest.withName(theName);

        String theNameFromTheUpdatedRequest = theUpdatedRequest.getName();
        assertThat(theNameFromTheUpdatedRequest).isEqualTo(theName);
    }

    @Test
    void doesNotSetTheNameForNullInput() {
        String thePredictedMessage = "Name is required to create an event";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createEventRequest.withName(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheNameForEmptyInput() {
        String thePredictedMessage = "Name is required to create an event";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createEventRequest.withName(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsStartDateTimeForValidInput() {
        ZonedDateTime theStartDateTime = ZonedDateTime.now();

        CreateEventRequest theUpdatedRequest = createEventRequest.withStartDateTime(theStartDateTime);

        ZonedDateTime theStartDateTimeFromTheUpdatedRequest = theUpdatedRequest.getStartDateTime();
        assertThat(theStartDateTimeFromTheUpdatedRequest).isEqualTo(theStartDateTime);
    }

    @Test
    void doesNotSetTheStartDateTimeForNullInput() {
        String thePredictedMessage = "Start date time is required to create an event";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createEventRequest.withStartDateTime(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsEndDateTimeForValidInput() {
        ZonedDateTime theEndDateTime = ZonedDateTime.now();

        CreateEventRequest theUpdatedRequest = createEventRequest.withEndDateTime(theEndDateTime);

        ZonedDateTime theEndDateTimeFromTheUpdatedRequest = theUpdatedRequest.getEndDateTime();
        assertThat(theEndDateTimeFromTheUpdatedRequest).isEqualTo(theEndDateTime);
    }

    @Test
    void doesNotSetTheEndDateTimeForNullInput() {
        String thePredictedMessage = "End date time is required to create an event";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createEventRequest.withEndDateTime(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}