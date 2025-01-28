package com.greenpalmsolutions.security.artifacts.api.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidateArtifactResponseTest {

    private ValidateArtifactResponse validateArtifactResponse;

    @Test
    void getsTheMessageIfIsValidIsTrue() {
        String thePredictedMessage = "Artifact is valid";
        validateArtifactResponse = new ValidateArtifactResponse(true);

        String theReturnedMessage = validateArtifactResponse.getMessage();

        assertThat(theReturnedMessage).isEqualTo(thePredictedMessage);
    }

    @Test
    void getsTheMessageIfIsValidIsFalse() {
        String thePredictedMessage = "Artifact is no longer valid; hash has been updated";
        validateArtifactResponse = new ValidateArtifactResponse(false);

        String theReturnedMessage = validateArtifactResponse.getMessage();

        assertThat(theReturnedMessage).isEqualTo(thePredictedMessage);
    }
}