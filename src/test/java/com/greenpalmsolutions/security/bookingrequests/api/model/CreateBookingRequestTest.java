package com.greenpalmsolutions.security.bookingrequests.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import com.greenpalmsolutions.security.events.api.model.CreateEventRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CreateBookingRequestTest {

    private CreateBookingRequest createBookingRequest;

    @BeforeEach
    void init() {
        createBookingRequest = new CreateBookingRequest();
    }

    @Test
    void setsRequesterEmailAddressForValidInput() {
        String theRequesterEmailAddress = "michael.scott@mail.com";

        CreateBookingRequest theUpdatedRequest = createBookingRequest.withRequesterEmailAddress(theRequesterEmailAddress);

        String theRequesterEmailAddressFromTheUpdatedRequest = theUpdatedRequest.getRequesterEmailAddress();
        assertThat(theRequesterEmailAddressFromTheUpdatedRequest).isEqualTo(theRequesterEmailAddress);
    }

    @Test
    void doesNotSetTheRequesterEmailAddressForNullInput() {
        String thePredictedMessage = "Requester email address is required to create a booking request";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createBookingRequest.withRequesterEmailAddress(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheRequesterEmailAddressForEmptyInput() {
        String thePredictedMessage = "Requester email address is required to create a booking request";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createBookingRequest.withRequesterEmailAddress(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheRequesterEmailAddressForInvalidInput() {
        String thePredictedMessage = "Requester email address must be in a valid format to create a booking request";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createBookingRequest.withRequesterEmailAddress("jim.halpert.mail.com"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsRequesteeEmailAddressForValidInput() {
        String theRequesteeEmailAddress = "jim.halpert@mail.com";

        CreateBookingRequest theUpdatedRequest = createBookingRequest.withRequesteeEmailAddress(theRequesteeEmailAddress);

        String theRequesteeEmailAddressFromTheUpdatedRequest = theUpdatedRequest.getRequesteeEmailAddress();
        assertThat(theRequesteeEmailAddressFromTheUpdatedRequest).isEqualTo(theRequesteeEmailAddress);
    }

    @Test
    void doesNotSetTheRequesteeEmailAddressForNullInput() {
        String thePredictedMessage = "Requestee email address is required to create a booking request";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createBookingRequest.withRequesteeEmailAddress(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheRequesteeEmailAddressForEmptyInput() {
        String thePredictedMessage = "Requestee email address is required to create a booking request";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createBookingRequest.withRequesteeEmailAddress(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheRequesteeEmailAddressForInvalidInput() {
        String thePredictedMessage = "Requestee email address must be in a valid format to create a booking request";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createBookingRequest.withRequesteeEmailAddress("jim.halpert.mail.com"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsEventNameForValidInput() {
        String theEventName = "My Meeting";

        CreateBookingRequest theUpdatedRequest = createBookingRequest.withEventName(theEventName);

        String theEventNameFromTheUpdatedRequest = theUpdatedRequest.getEventName();
        assertThat(theEventNameFromTheUpdatedRequest).isEqualTo(theEventName);
    }

    @Test
    void doesNotSetTheEventNameForNullInput() {
        String thePredictedMessage = "Event name is required to create a booking request";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createBookingRequest.withEventName(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheEventNameForEmptyInput() {
        String thePredictedMessage = "Event name is required to create a booking request";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createBookingRequest.withEventName(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsStartDateTimeForValidInput() {
        ZonedDateTime theStartDateTime = ZonedDateTime.now();

        CreateBookingRequest theUpdatedRequest = createBookingRequest.withStartDateTime(theStartDateTime);

        ZonedDateTime theStartDateTimeFromTheUpdatedRequest = theUpdatedRequest.getStartDateTime();
        assertThat(theStartDateTimeFromTheUpdatedRequest).isEqualTo(theStartDateTime);
    }

    @Test
    void doesNotSetTheStartDateTimeForNullInput() {
        String thePredictedMessage = "Start date time is required to create a booking request";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createBookingRequest.withStartDateTime(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsEndDateTimeForValidInput() {
        ZonedDateTime theEndDateTime = ZonedDateTime.now();

        CreateBookingRequest theUpdatedRequest = createBookingRequest.withEndDateTime(theEndDateTime);

        ZonedDateTime theEndDateTimeFromTheUpdatedRequest = theUpdatedRequest.getEndDateTime();
        assertThat(theEndDateTimeFromTheUpdatedRequest).isEqualTo(theEndDateTime);
    }

    @Test
    void doesNotSetTheEndDateTimeForNullInput() {
        String thePredictedMessage = "End date time is required to create a booking request";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createBookingRequest.withEndDateTime(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}
