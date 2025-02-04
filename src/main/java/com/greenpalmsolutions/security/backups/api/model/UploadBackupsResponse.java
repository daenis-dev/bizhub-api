package com.greenpalmsolutions.security.backups.api.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UploadBackupsResponse {

    private final long occupiedUserStorageInBytes;

    public String getOccupiedUserStorageInBytes() {
        if (occupiedUserStorageInBytes < 1000) {
            return occupiedUserStorageInBytes + " bytes";
        }

        final String[] units = { "bytes", "KB", "MB", "GB"};

        double size = occupiedUserStorageInBytes;
        int unitIndex = 0;

        while (size >= 1000 && unitIndex < units.length - 1) {
            size /= 1000;
            unitIndex++;
        }

        return String.format("%.1f %s", size, units[unitIndex]);
    }
}
