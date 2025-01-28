package com.greenpalmsolutions.security.artifacts.internal;

import com.greenpalmsolutions.security.accounts.api.behavior.FindCurrentAccount;
import com.greenpalmsolutions.security.artifacts.api.behavior.CreateArtifact;
import com.greenpalmsolutions.security.artifacts.api.behavior.ValidateArtifact;
import com.greenpalmsolutions.security.artifacts.api.behavior.ValidateArtifacts;
import com.greenpalmsolutions.security.artifacts.api.model.*;
import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ArtifactService implements CreateArtifact, ValidateArtifact, ValidateArtifacts {

    private final ArtifactRepository artifactRepository;
    private final FindCurrentAccount findCurrentAccount;

    @Override
    public CreateArtifactResponse createArtifactForRequest(CreateArtifactRequest request) {
        if (artifactRepository.maximumArtifactsExistForUserId(findCurrentAccount.getUserIdForCurrentAccount())) {
            throw new InvalidRequestException("User has reached the maximum number of allowed artifacts");
        }

        return artifactRepository.save(new Artifact()
                .mapFromCreateArtifactRequestAndUserId(request, findCurrentAccount.getUserIdForCurrentAccount()))
                .getAsCreateArtifactResponse();
    }

    @Override
    public ValidateArtifactResponse validateArtifactForRequest(ValidateArtifactRequest request) {
        Artifact artifact = artifactRepository.findByIdAndUserId(
                request.getId(), findCurrentAccount.getUserIdForCurrentAccount())
                .orElseThrow(() -> new RuntimeException("Cannot validate artifact that does not exist"));

        return new ValidateArtifactResponse(artifact.isValidWithinRequest(request));
    }

    @Override
    public ValidateArtifactsResponse validateArtifactsForRequest(ValidateArtifactsRequest request) {
        ValidateArtifactsResponse response = new ValidateArtifactsResponse();
        for (String artifactFilePath : request.getArtifactFilePaths()) {
            if (artifactIsCorruptForFilePathAndRequest(artifactFilePath, request)) {
                response.addCorruptArtifactFilePath(artifactFilePath);
            }
        }
        return response;
    }

    private boolean artifactIsCorruptForFilePathAndRequest(String filePath, ValidateArtifactsRequest request) {
        try {
            return artifactRepository.artifactIsModifiedWithFilePathAndUserIdAndHash(
                    filePath,
                    findCurrentAccount.getUserIdForCurrentAccount(),
                    Hex.decodeHex(request.getHashForFilePath(filePath)));
        } catch (DecoderException ex) {
            throw new RuntimeException("An error occurred while validating a hash value", ex);
        }
    }
}