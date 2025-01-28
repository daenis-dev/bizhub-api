package com.greenpalmsolutions.security.artifacts.api.behavior;

import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactRequest;
import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactResponse;

public interface ValidateArtifact {

    ValidateArtifactResponse validateArtifactForRequest(ValidateArtifactRequest request);
}
