package com.greenpalmsolutions.security.backups.api.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

// TODO: test
@Getter
public class DownloadBackupsRequest {

    private List<DownloadBackupRequest> backupRequests = new ArrayList<>();

    public DownloadBackupsRequest addFromListOfFileNamesAsString(String fileNames) {
        for (String fileName : fileNames.split(",")) {
            backupRequests.add(new DownloadBackupRequest().withFileName(fileName));
        }
        return this;
    }
}
