package com.greenpalmsolutions.security.artifacts.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateArtifactResponse {

    private final long id;
    private final String name;
    private final String filePath;
}
