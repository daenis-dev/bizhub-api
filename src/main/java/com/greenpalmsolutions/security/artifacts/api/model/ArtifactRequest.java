package com.greenpalmsolutions.security.artifacts.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtifactRequest {

    private String filePath;
    private String hash;

    public void setFilePath(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new InvalidRequestException("File path is required to create an artifact");
        }
        this.filePath = filePath;
    }

    public void setHash(String hash) {
        if (hash == null || hash.isEmpty()) {
            throw new InvalidRequestException("Hash is required to create an artifact");
        }
        this.hash = hash;
    }
}
