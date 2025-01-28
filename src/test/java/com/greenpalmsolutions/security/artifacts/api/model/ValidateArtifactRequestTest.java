package com.greenpalmsolutions.security.artifacts.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ValidateArtifactRequestTest {

    private ValidateArtifactRequest validateArtifactRequest;

    @BeforeEach
    void init() {
        validateArtifactRequest = new ValidateArtifactRequest();
    }

    @Test
    void setsIdForValidInput() {
        String theId = "2222";

        ValidateArtifactRequest theUpdatedRequest = validateArtifactRequest.withId(theId);

        Long theIdFromTheUpdatedRequest = theUpdatedRequest.getId();
        assertThat(theIdFromTheUpdatedRequest).isEqualTo(Long.parseLong(theId));
    }

    @Test
    void doesNotSetTheIdForNullInput() {
        String thePredictedMessage = "ID is required to validate an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> validateArtifactRequest.withId(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheIdForEmptyInput() {
        String thePredictedMessage = "ID is required to validate an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> validateArtifactRequest.withId(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheIdForNonNumericData() {
        String thePredictedMessage = "ID value must be numeric to validate an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> validateArtifactRequest.withId("dd33"));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsHashForValidInput() {
        String theHash = "4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f1";

        ValidateArtifactRequest theUpdatedRequest = validateArtifactRequest.withHash(theHash);

        String theHashFromTheUpdatedRequest = theUpdatedRequest.getHash();
        assertThat(theHashFromTheUpdatedRequest).isEqualTo(theHash);
    }

    @Test
    void doesNotSetTheHashForNullInput() {
        String thePredictedMessage = "Hash is required to validate an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> validateArtifactRequest.withHash(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheHashForEmptyInput() {
        String thePredictedMessage = "Hash is required to validate an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> validateArtifactRequest.withHash(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}