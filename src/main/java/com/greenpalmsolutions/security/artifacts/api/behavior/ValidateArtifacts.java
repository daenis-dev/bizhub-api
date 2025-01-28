package com.greenpalmsolutions.security.artifacts.api.behavior;

import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactsRequest;
import com.greenpalmsolutions.security.artifacts.api.model.ValidateArtifactsResponse;

public interface ValidateArtifacts {

    ValidateArtifactsResponse validateArtifactsForRequest(ValidateArtifactsRequest request);
}
