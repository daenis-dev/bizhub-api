package com.greenpalmsolutions.security.files.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.Getter;

// TODO: test
@Getter
public class UploadFileRequest {

    private byte[] fileContents;
    private String filePath;

    public UploadFileRequest withFilePath(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new InvalidRequestException("File path is required to upload file");
        }
        this.filePath = filePath;
        return this;
    }

    public UploadFileRequest withFileContents(byte[] fileContents) {
        if (fileContents == null || fileContents.length == 0) {
            throw new InvalidRequestException("File contents are required to upload file");
        }
        this.fileContents = fileContents;
        return this;
    }
}
