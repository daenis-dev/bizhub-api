package com.greenpalmsolutions.security.artifacts.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidateArtifactsRequestTest {

    private ValidateArtifactsRequest validateArtifactsRequest;

    @BeforeEach
    void init() {
        validateArtifactsRequest = new ValidateArtifactsRequest();
    }

    @Test
    void setsArtifactHashForValidInput() {
        String theArtifactHash = "C:/some/file.txt:123456,C:/another/sample.txt:7891011";

        validateArtifactsRequest.withArtifactHashes(theArtifactHash);

        Map<String, String> theReturnedArtifactHashes = validateArtifactsRequest.getArtifactHashes();
        assertThat(theReturnedArtifactHashes.size()).isEqualTo(2);
        assertThat(theReturnedArtifactHashes.get("C:/some/file.txt")).isEqualTo("123456");
    }

    @Test
    void doesNotSetTheArtifactHashesForNullInput() {
        String thePredictedMessage = "Artifact hashes are required to validate artifacts";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> validateArtifactsRequest.withArtifactHashes(null));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void doesNotSetTheArtifactHashesForEmptyInput() {
        String thePredictedMessage = "Artifact hashes are required to validate artifacts";

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> validateArtifactsRequest.withArtifactHashes(""));

        String theMessageFromTheException = theException.getMessage();
        assertThat(theMessageFromTheException).isEqualTo(thePredictedMessage);
    }

    @Test
    void getsArtifactFilePaths() {
        String theArtifactHashes = "C:/some/file.txt:123456,C:/another/sample.txt:7891011";
        validateArtifactsRequest.withArtifactHashes(theArtifactHashes);

        Set<String> theArtifactFilePaths = validateArtifactsRequest.getArtifactFilePaths();

        assertThat(theArtifactFilePaths).contains("C:/some/file.txt");
        assertThat(theArtifactFilePaths).contains("C:/another/sample.txt");
    }

    @Test
    void getsHashForFilePath() {
        String theArtifactHashes = "C:/some/file.txt:123456,C:/another/sample.txt:7891011";
        validateArtifactsRequest.withArtifactHashes(theArtifactHashes);

        String theReturnedHash = validateArtifactsRequest.getHashForFilePath("C:/some/file.txt");

        assertThat(theReturnedHash).isEqualTo("123456");
    }
}