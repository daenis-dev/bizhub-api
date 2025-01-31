package com.greenpalmsolutions.security.artifacts.internal;

import com.greenpalmsolutions.security.accounts.api.behavior.FindCurrentAccount;
import com.greenpalmsolutions.security.artifacts.api.behavior.CreateArtifacts;
import com.greenpalmsolutions.security.artifacts.api.behavior.ValidateArtifacts;
import com.greenpalmsolutions.security.artifacts.api.model.*;
import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class ArtifactsService implements CreateArtifacts, ValidateArtifacts {

    private final ArtifactRepository artifactRepository;
    private final FindCurrentAccount findCurrentAccount;

    @Override
    public void createArtifactForRequest(List<ArtifactRequest> requests) {
        for (ArtifactRequest request: requests) {
            if (artifactRepository.maximumArtifactsExistForUserId(findCurrentAccount.getUserIdForCurrentAccount())) {
                throw new InvalidRequestException("User has reached the maximum number of allowed artifacts");
            }
            artifactRepository.save(new Artifact()
                            .mapFromCreateArtifactRequestAndUserId(request, findCurrentAccount.getUserIdForCurrentAccount()));
        }
    }

    @Override
    public ValidateArtifactsResponse validateArtifactsForRequests(List<ArtifactRequest> requests) {
        ValidateArtifactsResponse response = new ValidateArtifactsResponse();
        for (ArtifactRequest request : requests) {
            if (artifactIsCorruptForRequest(request)) {
                response.addCorruptArtifactFilePath(request.getFilePath());
            }
        }
        return response;
    }

    private boolean artifactIsCorruptForRequest(ArtifactRequest request) {
        try {
            return artifactRepository.artifactIsModifiedWithFilePathAndUserIdAndHash(
                    request.getFilePath(),
                    findCurrentAccount.getUserIdForCurrentAccount(),
                    Hex.decodeHex(request.getHash()));
        } catch (DecoderException ex) {
            throw new RuntimeException("An error occurred while validating a hash value", ex);
        }
    }
}