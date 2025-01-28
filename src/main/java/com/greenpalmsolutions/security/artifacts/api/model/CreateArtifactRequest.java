package com.greenpalmsolutions.security.artifacts.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.Getter;

@Getter
public class CreateArtifactRequest {

    private String name;
    private String filePath;
    private String hash;

    public CreateArtifactRequest withName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidRequestException("Name is required to create an artifact");
        }
        this.name = name;
        return this;
    }

    public CreateArtifactRequest withFilePath(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new InvalidRequestException("File path is required to create an artifact");
        }
        this.filePath = filePath;
        return this;
    }

    public CreateArtifactRequest withHash(String hash) {
        if (hash == null || hash.isEmpty()) {
            throw new InvalidRequestException("Hash is required to create an artifact");
        }
        this.hash = hash;
        return this;
    }
}
