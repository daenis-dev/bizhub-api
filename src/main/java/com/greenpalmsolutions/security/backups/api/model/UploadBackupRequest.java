package com.greenpalmsolutions.security.backups.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.Getter;

@Getter
public class UploadBackupRequest {

    private String fileName;
    private byte[] fileContents;

    public UploadBackupRequest withFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new InvalidRequestException("File name is required to create backup");
        }
        this.fileName = fileName;
        return this;
    }

    public UploadBackupRequest withFileContents(byte[] fileContents) {
        if (fileContents == null || fileContents.length == 0) {
            throw new InvalidRequestException("File contents are required to create backup");
        }
        this.fileContents = fileContents;
        return this;
    }

    public int getContentLengthInBytes() {
        return fileContents.length;
    }

    public String getFileExtension() {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1);
    }

    public String getFileNameWithoutExtension() {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, lastDotIndex);
    }
}
