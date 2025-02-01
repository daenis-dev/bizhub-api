package com.greenpalmsolutions.security.backups.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.Getter;

@Getter
public class DownloadBackupRequest {

    private String fileName;

    public DownloadBackupRequest withFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new InvalidRequestException("File name is required to download backup");
        }
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
