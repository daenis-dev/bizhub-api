package com.greenpalmsolutions.security.artifacts.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidateArtifactsResponseTest {

    private ValidateArtifactsResponse validateArtifactsResponse;

    @BeforeEach
    void init() {
        validateArtifactsResponse = new ValidateArtifactsResponse();
    }

    @Test
    void addsCorruptArtifactFilePath() {
        String corruptFilePath = "/something/corrupt.txt";

        validateArtifactsResponse.addCorruptArtifactFilePath(corruptFilePath);

        assertThat(validateArtifactsResponse.getCorruptArtifactFilePaths()).contains(corruptFilePath);
    }
}
