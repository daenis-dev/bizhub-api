package com.greenpalmsolutions.security.accounts.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResetPasswordRequestTest {

    private ResetPasswordRequest resetPasswordRequest;

    @BeforeEach
    void init() {
        resetPasswordRequest = new ResetPasswordRequest();
    }

    @Test
    void setsEmailAddressForValidInput() {
        String theEmailAddress = "jim.recard@mail.com";

        ResetPasswordRequest theUpdatedRequest = resetPasswordRequest.withEmailAddress(theEmailAddress);

        String theEmailAddressFromTheUpdatedRequest = theUpdatedRequest.getEmailAddress();
        assertThat(theEmailAddressFromTheUpdatedRequest).isEqualTo(theEmailAddress);
    }

    @Test
    void doesNotSetTheEmailAddressForNullInput() {
        String thePredictedMessage = "Email address is required to reset password";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> resetPasswordRequest.withEmailAddress(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheEmailAddressForEmptyInput() {
        String thePredictedMessage = "Email address is required to reset password";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> resetPasswordRequest.withEmailAddress(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheEmailAddressForInvalidInput() {
        String thePredictedMessage = "Email address must be in a valid format to reset password";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> resetPasswordRequest.withEmailAddress("jim.recard.mail.com"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}