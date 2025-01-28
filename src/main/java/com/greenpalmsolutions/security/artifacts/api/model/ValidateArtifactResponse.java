package com.greenpalmsolutions.security.artifacts.api.model;

import lombok.Getter;

@Getter
public class ValidateArtifactResponse {

    private final boolean isValid;

    public ValidateArtifactResponse(boolean isValid) {
        this.isValid = isValid;
    }

    public String getMessage() {
        return isValid ? "Artifact is valid" : "Artifact is no longer valid; hash has been updated";
    }
}
