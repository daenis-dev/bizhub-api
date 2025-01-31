package com.greenpalmsolutions.security.artifacts.internal;

import com.greenpalmsolutions.security.artifacts.api.model.ArtifactRequest;
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
        ArtifactRequest theRequest = new ArtifactRequest();
        theRequest.setFilePath("C:/some/file.txt");
        theRequest.setHash("4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f1");
        String theUserId = "abc-123";

        artifact.mapFromCreateArtifactRequestAndUserId(theRequest, theUserId);

        assertThat(artifact.getFilePath()).isEqualTo("C:/some/file.txt");
        assertThat(artifact.getHash()).isEqualTo(Hex.decodeHex("4aebd3d8e8f4f63a1e90cd4fa275f9abf7d8c682bd8f95cd74c5a9b29f9e53f1"));
        assertThat(artifact.getUserId()).isEqualTo("abc-123");
    }
}