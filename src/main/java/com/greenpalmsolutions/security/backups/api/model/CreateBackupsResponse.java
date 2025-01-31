package com.greenpalmsolutions.security.backups.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO: test
@Getter
@AllArgsConstructor
public class CreateBackupsResponse {

    private final int sizeInBytes;

    public String getFormattedSize() {
        if (sizeInBytes < 1000) {
            return sizeInBytes + " bytes";
        }

        final String[] units = { "bytes", "KB", "MB", "GB", "TB", "PB", "EB" };

        double size = sizeInBytes;
        int unitIndex = 0;

        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }

        return String.format("%.1f %s", size, units[unitIndex]);
    }
}
