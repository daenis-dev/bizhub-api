package com.greenpalmsolutions.security.artifacts.api.behavior;

import com.greenpalmsolutions.security.artifacts.api.model.ArtifactRequest;

import java.util.List;

public interface CreateArtifacts {

    void createArtifactForRequest(List<ArtifactRequest> requests);
}
