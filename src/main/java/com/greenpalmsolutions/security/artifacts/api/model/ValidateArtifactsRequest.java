package com.greenpalmsolutions.security.artifacts.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class ValidateArtifactsRequest {

    private Map<String, String> artifactHashes = new HashMap<>();

    public ValidateArtifactsRequest withArtifactHashes(String artifactHashes) {
        if (artifactHashes == null || artifactHashes.isEmpty()) {
            throw new InvalidRequestException("Artifact hashes are required to validate artifacts");
        }
        String[] artifactHashesAsList = artifactHashes.split(",");
        for (String artifactHash : artifactHashesAsList) {
            int lastColonIndex = artifactHash.lastIndexOf(":");
            String filePath = artifactHash.substring(0, lastColonIndex);
            String hash = artifactHash.substring(lastColonIndex + 1);
            this.artifactHashes.put(filePath, hash);
        }

        return this;
    }

    public Set<String> getArtifactFilePaths() {
        return artifactHashes.keySet();
    }

    public String getHashForFilePath(String filePath) {
        return artifactHashes.get(filePath);
    }
}
