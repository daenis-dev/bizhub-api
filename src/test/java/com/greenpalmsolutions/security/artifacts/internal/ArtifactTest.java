package com.greenpalmsolutions.security.artifacts.internal;

import com.greenpalmsolutions.security.artifacts.api.model.CreateArtifactRequest;
import com.greenpalmsolutions.security.artifacts.api.model.CreateArtifactResponse;
import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactRequest;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArtifactTest {

    private Artifact artifact;

    @BeforeEach
    void init() {
        artifact = new Artifact();
    }

    @Test
    void mapsFromCreateArtifactRequestAndUserId() throws Exception {
        CreateArtifactRequest theRequest = new CreateArtifactRequest()
                .withName("File")
                .withFilePath("C:/some/file.txt")
                .withHash("123456");
        String theUserId = "abc-123";

        artifact.mapFromCreateArtifactRequestAndUserId(theRequest, theUserId);

        assertThat(artifact.getName()).isEqualTo("File");
        assertThat(artifact.getFilePath()).isEqualTo("C:/some/file.txt");
        assertThat(artifact.getHash()).isEqualTo(Hex.decodeHex("123456"));
        assertThat(artifact.getUserId()).isEqualTo("abc-123");
    }

    @Test
    void getsAsCreateArtifactResponse() {
        artifact.setId(1L);
        artifact.setName("File");
        artifact.setFilePath("C:/some/file.txt");

        CreateArtifactResponse theResponse = artifact.getAsCreateArtifactResponse();

        assertThat(theResponse.getId()).isEqualTo(1L);
        assertThat(theResponse.getName()).isEqualTo("File");
        assertThat(theResponse.getFilePath()).isEqualTo("C:/some/file.txt");
    }

    @Test
    void artifactIsValidWithinRequestIfHashHasNotChanged() throws Exception {
        ValidateArtifactRequest request = new ValidateArtifactRequest().withHash("123456");
        artifact.setHash(Hex.decodeHex("123456"));

        boolean artifactIsValid = artifact.isValidWithinRequest(request);

        assertThat(artifactIsValid).isTrue();
    }

    @Test
    void artifactIsNotValidWithinRequestIfHashHasChanged() throws Exception {
        ValidateArtifactRequest request = new ValidateArtifactRequest().withHash("123454");
        artifact.setHash(Hex.decodeHex("123456"));

        boolean artifactIsValid = artifact.isValidWithinRequest(request);

        assertThat(artifactIsValid).isFalse();
    }
}