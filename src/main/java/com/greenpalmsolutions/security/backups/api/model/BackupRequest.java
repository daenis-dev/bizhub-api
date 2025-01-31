package com.greenpalmsolutions.security.backups.api.model;

import lombok.Getter;

// TODO: test
@Getter
public class BackupRequest {

    private String fileName;
    private byte[] fileContents;

    public BackupRequest withFileName(String fileName) {
        // TODO: validation
        this.fileName = fileName;
        return this;
    }

    public BackupRequest withFileContents(byte[] fileContents) {
        // TODO: validation
        this.fileContents = fileContents;
        return this;
    }
}
