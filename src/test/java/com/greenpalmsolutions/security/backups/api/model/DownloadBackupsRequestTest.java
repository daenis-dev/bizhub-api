package com.greenpalmsolutions.security.backups.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DownloadBackupsRequestTest {

    private DownloadBackupsRequest downloadBackupsRequest;

    @BeforeEach
    void init() {
        downloadBackupsRequest = new DownloadBackupsRequest();
    }

    @Test
    void addsFromTheListOfFileNamesAsString() {
        String theFileNames = "file1.txt,file2.txt";

        downloadBackupsRequest.addFromListOfFileNamesAsString(theFileNames);

        List<String> theMappedFileNames = downloadBackupsRequest.getBackupRequests().stream().map(
                DownloadBackupRequest::getFileName).toList();
        assertThat(theMappedFileNames).contains("file1.txt");
        assertThat(theMappedFileNames).contains("file2.txt");
    }
}