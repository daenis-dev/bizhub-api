package com.greenpalmsolutions.security.artifacts.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ArtifactRequestTest {

    private ArtifactRequest artifactRequest;

    @BeforeEach
    void init() {
        artifactRequest = new ArtifactRequest();
    }

    @Test
    void setsFilePathForValidInput() {
        String theFilePath = "C:\\Windows\\System32\\ntoskrnl.exe";

        artifactRequest.setFilePath(theFilePath);

        String theFilePathFromTheUpdatedRequest = artifactRequest.getFilePath();
        assertThat(theFilePathFromTheUpdatedRequest).isEqualTo(theFilePath);
    }

    @Test
    void doesNotSetTheFilePathForNullInput() {
        String thePredictedMessage = "File path is required to create an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> artifactRequest.setFilePath(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheFilePathForEmptyInput() {
        String thePredictedMessage = "File path is required to create an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> artifactRequest.setFilePath(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsHashForValidInput() {
        String theHash = "4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f1";

        artifactRequest.setHash(theHash);

        String theHashFromTheUpdatedRequest = artifactRequest.getHash();
        assertThat(theHashFromTheUpdatedRequest).isEqualTo(theHash);
    }

    @Test
    void doesNotSetTheHashForNullInput() {
        String thePredictedMessage = "Hash is required to create an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> artifactRequest.setHash(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheHashForEmptyInput() {
        String thePredictedMessage = "Hash is required to create an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> artifactRequest.setHash(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}