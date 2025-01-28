package com.greenpalmsolutions.security.accounts.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationRequestTest {

    private RegistrationRequest registrationRequest;

    @BeforeEach
    void init() {
        registrationRequest = new RegistrationRequest();
    }

    @Test
    void setsEmailAddressForValidInput() {
        String theEmailAddress = "jimmy.recard@jr.com";

        RegistrationRequest theUpdatedRequest = registrationRequest.withEmailAddress(theEmailAddress);

        String theEmailAddressFromTheUpdatedRequest = theUpdatedRequest.getEmailAddress();
        assertThat(theEmailAddressFromTheUpdatedRequest).isEqualTo(theEmailAddress);
    }

    @Test
    void doesNotSetTheEmailAddressForNullInput() {
        String thePredictedMessage = "Email address is required to register for an account";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> registrationRequest.withEmailAddress(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheEmailAddressForEmptyInput() {
        String thePredictedMessage = "Email address is required to register for an account";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> registrationRequest.withEmailAddress(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheEmailAddressForInvalidInput() {
        String thePredictedMessage = "Email address must be in a valid format to register for an account";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> registrationRequest.withEmailAddress("jim.recard.mail.com"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsPasswordForValidInput() {
        String thePassword = "changeit88";

        RegistrationRequest theUpdatedRequest = registrationRequest.withPassword(thePassword);

        String thePasswordFromTheUpdatedRequest = theUpdatedRequest.getPassword();
        assertThat(thePasswordFromTheUpdatedRequest).isEqualTo(thePassword);
    }

    @Test
    void doesNotSetThePasswordForNullInput() {
        String thePredictedMessage = "Password is required to register for an account";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> registrationRequest.withPassword(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetThePasswordForEmptyInput() {
        String thePredictedMessage = "Password is required to register for an account";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> registrationRequest.withPassword(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetThePasswordForLessThanEightCharacters() {
        String thePredictedMessage = "Password must be at least eight characters to register for an account";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> registrationRequest.withPassword("short1"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetThePasswordWithoutOneLetterAndOneNumber() {
        String thePredictedMessage = "Password must contain at least one letter and one number to register for an account";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> registrationRequest.withPassword("nonumbersallowed"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void confirmPasswordDoesNothingIfPasswordsMatch() {
        String thePassword = "changeit88";
        registrationRequest.withPassword(thePassword);

        registrationRequest.confirmPassword(thePassword);
    }

    @Test
    void confirmPasswordThrowsAnExceptionIfPasswordsDoNotMatch() {
        String thePredictedMessage = "Passwords do not match";
        registrationRequest.withPassword("changeit_1");

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> registrationRequest.confirmPassword("changeit_2"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}