package com.greenpalmsolutions.security.artifacts.api.behavior;

import com.greenpalmsolutions.security.artifacts.api.model.CreateArtifactRequest;
import com.greenpalmsolutions.security.artifacts.api.model.CreateArtifactResponse;

public interface CreateArtifact {

    CreateArtifactResponse createArtifactForRequest(CreateArtifactRequest request);
}
