package com.greenpalmsolutions.security.artifacts.api.controller;

import com.greenpalmsolutions.security.artifacts.api.behavior.CreateArtifact;
import com.greenpalmsolutions.security.artifacts.api.behavior.FindArtifacts;
import com.greenpalmsolutions.security.artifacts.api.behavior.ValidateArtifact;
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

    private final CreateArtifact createArtifact;
    private final FindArtifacts findArtifacts;
    private final ValidateArtifact validateArtifact;
    private final ValidateArtifacts validateArtifacts;

    @PostMapping("/v1/artifacts")
    public ResponseEntity<CreateArtifactResponse> createArtifact(
            @RequestParam("name") String name,
            @RequestParam("file-path") String filePath,
            @RequestParam("hash") String hash) {
        return new ResponseEntity<>(createArtifact.createArtifactForRequest(
                new CreateArtifactRequest()
                        .withName(name)
                        .withFilePath(filePath)
                        .withHash(hash)), HttpStatus.CREATED);
    }

    @GetMapping("/v1/artifacts")
    public ResponseEntity<List<ArtifactDetails>> findArtifacts() {
        return new ResponseEntity<>(findArtifacts.findArtifacts(), HttpStatus.OK);
    }

    @GetMapping("/v1/artifacts/{id}/validate")
    public ResponseEntity<ValidateArtifactResponse> validateArtifact(
            @PathVariable("id") String id,
            @RequestParam("hash") String hash) {
        return new ResponseEntity<>(validateArtifact.validateArtifactForRequest(
                new ValidateArtifactRequest()
                        .withId(id)
                        .withHash(hash)), HttpStatus.OK);
    }

    @GetMapping("/v1/artifacts/validate")
    public ResponseEntity<ValidateArtifactsResponse> validateArtifacts(
            @RequestParam("artifact-hashes") String artifactHashes) {
        return new ResponseEntity<>(validateArtifacts.validateArtifactsForRequest(
                new ValidateArtifactsRequest()
                        .withArtifactHashes(artifactHashes)), HttpStatus.OK);
    }
}
