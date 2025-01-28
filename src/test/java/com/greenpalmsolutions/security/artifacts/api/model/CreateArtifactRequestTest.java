package com.greenpalmsolutions.security.artifacts.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CreateArtifactRequestTest {

    private CreateArtifactRequest createArtifactRequest;

    @BeforeEach
    void init() {
        createArtifactRequest = new CreateArtifactRequest();
    }

    @Test
    void setsNameForValidInput() {
        String theName = "Windows Kernel";

        CreateArtifactRequest theUpdatedRequest = createArtifactRequest.withName(theName);

        String theNameFromTheUpdatedRequest = theUpdatedRequest.getName();
        assertThat(theNameFromTheUpdatedRequest).isEqualTo(theName);
    }

    @Test
    void doesNotSetTheNameForNullInput() {
        String thePredictedMessage = "Name is required to create an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createArtifactRequest.withName(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheNameForEmptyInput() {
        String thePredictedMessage = "Name is required to create an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createArtifactRequest.withName(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsFilePathForValidInput() {
        String theFilePath = "C:\\Windows\\System32\\ntoskrnl.exe";

        CreateArtifactRequest theUpdatedRequest = createArtifactRequest.withFilePath(theFilePath);

        String theFilePathFromTheUpdatedRequest = theUpdatedRequest.getFilePath();
        assertThat(theFilePathFromTheUpdatedRequest).isEqualTo(theFilePath);
    }

    @Test
    void doesNotSetTheFilePathForNullInput() {
        String thePredictedMessage = "File path is required to create an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createArtifactRequest.withFilePath(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheFilePathForEmptyInput() {
        String thePredictedMessage = "File path is required to create an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createArtifactRequest.withFilePath(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void setsHashForValidInput() {
        String theHash = "4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f1";

        CreateArtifactRequest theUpdatedRequest = createArtifactRequest.withHash(theHash);

        String theHashFromTheUpdatedRequest = theUpdatedRequest.getHash();
        assertThat(theHashFromTheUpdatedRequest).isEqualTo(theHash);
    }

    @Test
    void doesNotSetTheHashForNullInput() {
        String thePredictedMessage = "Hash is required to create an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createArtifactRequest.withHash(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheHashForEmptyInput() {
        String thePredictedMessage = "Hash is required to create an artifact";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> createArtifactRequest.withHash(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }
}