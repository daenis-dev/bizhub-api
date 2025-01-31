package com.greenpalmsolutions.security.artifacts.api.behavior;

import com.greenpalmsolutions.security.artifacts.api.model.ArtifactRequest;
import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactsResponse;

import java.util.List;

public interface ValidateArtifacts {

    ValidateArtifactsResponse validateArtifactsForRequests(List<ArtifactRequest> requests);
}
