package com.greenpalmsolutions.security.artifacts.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ValidateArtifactsResponse {

    private List<String> corruptArtifactFilePaths = new ArrayList<>();

    public ValidateArtifactsResponse addCorruptArtifactFilePath(String filePath) {
        corruptArtifactFilePaths.add(filePath);
        return this;
    }
}
