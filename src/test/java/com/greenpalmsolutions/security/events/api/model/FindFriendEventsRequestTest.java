package com.greenpalmsolutions.security.events.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FindFriendEventsRequestTest {

    private FindFriendEventsRequest findFriendEventsRequest;

    @BeforeEach
    void init() {
        findFriendEventsRequest = new FindFriendEventsRequest();
    }

    @Test
    void setsUsernameForValidInput() {
        String theUsername = "jim.recard@mail.com";

        FindFriendEventsRequest theUpdatedRequest = findFriendEventsRequest.withUsername(theUsername);

        String theUsernameFromTheUpdatedRequest = theUpdatedRequest.getUsername();
        assertThat(theUsernameFromTheUpdatedRequest).isEqualTo(theUsername);
    }

    @Test
    void doesNotSetTheUsernameForNullInput() {
        String thePredictedMessage = "Username is required to find friend events";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> findFriendEventsRequest.withUsername(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheUsernameForEmptyInput() {
        String thePredictedMessage = "Username is required to find friend events";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> findFriendEventsRequest.withUsername(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheUsernameForInvalidInput() {
        String thePredictedMessage = "Username must be in a valid format to find friend events";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> findFriendEventsRequest.withUsername("jim.recard.mail.com"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}