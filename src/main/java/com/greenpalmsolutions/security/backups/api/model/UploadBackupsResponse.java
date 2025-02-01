package com.greenpalmsolutions.security.backups.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UploadBackupsResponse {

    private final long sizeInBytes;

    public String getFormattedSize() {
        if (sizeInBytes < 1000) {
            return sizeInBytes + " bytes";
        }

        final String[] units = { "bytes", "KB", "MB", "GB"};

        double size = sizeInBytes;
        int unitIndex = 0;

        while (size >= 1000 && unitIndex < units.length - 1) {
            size /= 1000;
            unitIndex++;
        }

        return String.format("%.1f %s", size, units[unitIndex]);
    }
}
