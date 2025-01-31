package com.greenpalmsolutions.security.artifacts.api.controller;

import com.greenpalmsolutions.security.artifacts.api.behavior.CreateArtifacts;
import com.greenpalmsolutions.security.artifacts.api.behavior.ValidateArtifacts;
import com.greenpalmsolutions.security.artifacts.api.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArtifactController {

    private final CreateArtifacts createArtifacts;
    private final ValidateArtifacts validateArtifacts;

    @PostMapping("/v1/artifacts")
    public ResponseEntity<?> createArtifact(@RequestBody List<ArtifactRequest> requests) {
        createArtifacts.createArtifactForRequest(requests);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/v1/artifacts/validate")
    public ResponseEntity<ValidateArtifactsResponse> validateArtifacts(
            @RequestBody List<ArtifactRequest> requests) {
        return new ResponseEntity<>(validateArtifacts.validateArtifactsForRequests(requests), HttpStatus.OK);
    }
}
