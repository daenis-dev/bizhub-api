package com.greenpalmsolutions.security.artifacts.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.Getter;

@Getter
public class ValidateArtifactRequest {

    private long id;
    private String hash;

    public ValidateArtifactRequest withId(String id) {
        if (id == null || id.isEmpty()) {
            throw new InvalidRequestException("ID is required to validate an artifact");
        }
        boolean theIdContainsNonNumericCharacters = !id.matches("\\d*");
        if (theIdContainsNonNumericCharacters) {
            throw new InvalidRequestException("ID value must be numeric to validate an artifact");
        }
        this.id = Long.parseLong(id);
        return this;
    }

    public ValidateArtifactRequest withHash(String hash) {
        if (hash == null || hash.isEmpty()) {
            throw new InvalidRequestException("Hash is required to validate an artifact");
        }
        this.hash = hash;
        return this;
    }
}
