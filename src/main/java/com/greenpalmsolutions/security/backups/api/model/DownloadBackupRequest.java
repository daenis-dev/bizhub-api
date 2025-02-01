package com.greenpalmsolutions.security.backups.api.model;

import lombok.Getter;

// TODO: test
@Getter
public class DownloadBackupRequest {

    private String fileName;

    public DownloadBackupRequest withFileName(String fileName) {
        // TODO: validation
        this.fileName = fileName;
        return this;
    }

    public String getFileNameWithoutExtension() {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, lastDotIndex);
    }
}
