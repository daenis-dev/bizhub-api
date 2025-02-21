package com.greenpalmsolutions.security.events.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FindEventDateTimesRequestTest {

    private FindEventDateTimesRequest findEventDateTimesRequest;

    @BeforeEach
    void init() {
        findEventDateTimesRequest = new FindEventDateTimesRequest();
    }

    @Test
    void setsUsernameForValidInput() {
        String theUsername = "jim.recard@mail.com";

        FindEventDateTimesRequest theUpdatedRequest = findEventDateTimesRequest.withUsername(theUsername);

        String theUsernameFromTheUpdatedRequest = theUpdatedRequest.getUsername();
        assertThat(theUsernameFromTheUpdatedRequest).isEqualTo(theUsername);
    }

    @Test
    void doesNotSetTheUsernameForNullInput() {
        String thePredictedMessage = "Username is required to find event date times";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> findEventDateTimesRequest.withUsername(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheUsernameForEmptyInput() {
        String thePredictedMessage = "Username is required to find event date times";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> findEventDateTimesRequest.withUsername(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheUsernameForInvalidInput() {
        String thePredictedMessage = "Username must be in a valid format to find event date times";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> findEventDateTimesRequest.withUsername("jim.recard.mail.com"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}