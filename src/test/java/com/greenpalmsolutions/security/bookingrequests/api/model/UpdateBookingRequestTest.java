package com.greenpalmsolutions.security.bookingrequests.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UpdateBookingRequestTest {

    private UpdateBookingRequest updateBookingRequest;

    @BeforeEach
    void init() {
        updateBookingRequest = new UpdateBookingRequest();
    }

    @Test
    void setsBookingRequestIdForValidInput() {
        String theId = "2222";

        UpdateBookingRequest theUpdatedRequest = updateBookingRequest.withBookingRequestId(theId);

        Long theIdFromTheUpdatedRequest = theUpdatedRequest.getBookingRequestId();
        assertThat(theIdFromTheUpdatedRequest).isEqualTo(Long.parseLong(theId));
    }

    @Test
    void doesNotSetTheBookingRequestIdForNullInput() {
        String thePredictedMessage = "Cannot update a booking request without a booking request ID";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> updateBookingRequest.withBookingRequestId(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheBookingRequestIdForEmptyInput() {
        String thePredictedMessage = "Cannot update a booking request without a booking request ID";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> updateBookingRequest.withBookingRequestId(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheBookingRequestIdForNonNumericData() {
        String thePredictedMessage = "Cannot update a booking request for a non numeric booking request ID";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> updateBookingRequest.withBookingRequestId("dd33"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsStatusNameForValidInput() {
        String theStatusName = "accepted";

        UpdateBookingRequest theUpdatedRequest = updateBookingRequest.withStatusName(theStatusName);

        String theStatusNameFromTheUpdatedRequest = theUpdatedRequest.getStatusName();
        assertThat(theStatusNameFromTheUpdatedRequest).isEqualTo(theStatusName);
    }

    @Test
    void doesNotSetTheStatusNameForNullInput() {
        String thePredictedMessage = "Cannot update a booking request without a status";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> updateBookingRequest.withStatusName(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheStatusNameForEmptyInput() {
        String thePredictedMessage = "Cannot update a booking request without a status";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> updateBookingRequest.withStatusName(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheStatusNameForAnythingOtherThanAcceptedOrDeniedOrCanceled() {
        String thePredictedMessage = "Cannot update a booking request with an invalid status";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> updateBookingRequest.withStatusName("pending approval"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}
