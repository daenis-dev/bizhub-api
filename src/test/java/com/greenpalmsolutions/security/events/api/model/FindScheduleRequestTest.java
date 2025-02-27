package com.greenpalmsolutions.security.events.api.model;

import com.greenpalmsolutions.security.accounts.api.model.LoginRequest;
import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FindScheduleRequestTest {

    private FindScheduleRequest findScheduleRequest;

    @BeforeEach
    void init() {
        findScheduleRequest = new FindScheduleRequest();
    }

    @Test
    void setsUsernameForValidInput() {
        String theUsername = "jim.recard@mail.com";

        FindScheduleRequest theUpdatedRequest = findScheduleRequest.withUsername(theUsername);

        String theEmailAddressFromTheUpdatedRequest = theUpdatedRequest.getUsername();
        assertThat(theEmailAddressFromTheUpdatedRequest).isEqualTo(theUsername);
    }

    @Test
    void doesNotSetTheUsernameForNullInput() {
        String thePredictedMessage = "Cannot find schedule without username";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> findScheduleRequest.withUsername(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheUsernameForEmptyInput() {
        String thePredictedMessage = "Cannot find schedule without username";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> findScheduleRequest.withUsername(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheUsernameForInvalidInput() {
        String thePredictedMessage = "Username must be in a valid format to find schedule";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> findScheduleRequest.withUsername("jim.recard.mail.com"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsScheduleKeyForValidInput() {
        String theScheduleKey = "xyz123abc";

        FindScheduleRequest theUpdatedRequest = findScheduleRequest.withScheduleKey(theScheduleKey);

        String theScheduleKeyFromTheUpdatedRequest = theUpdatedRequest.getScheduleKey();
        assertThat(theScheduleKeyFromTheUpdatedRequest).isEqualTo(theScheduleKey);
    }

    @Test
    void doesNotSetTheScheduleKeyForNullInput() {
        String thePredictedMessage = "Cannot find schedule without schedule key";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> findScheduleRequest.withScheduleKey(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheScheduleKeyForEmptyInput() {
        String thePredictedMessage = "Cannot find schedule without schedule key";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> findScheduleRequest.withScheduleKey(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}