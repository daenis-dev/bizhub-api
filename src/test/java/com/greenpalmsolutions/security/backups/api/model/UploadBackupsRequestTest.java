package com.greenpalmsolutions.security.backups.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UploadBackupsRequestTest {

    private UploadBackupsRequest uploadBackupsRequest;

    @BeforeEach
    void init() {
        uploadBackupsRequest = new UploadBackupsRequest();
    }

    @Test
    void addsFromFiles() {
        MockMultipartFile file1 = new MockMultipartFile(
                "files", "file1.txt", "text/plain", "Hello, World!".getBytes());
        MockMultipartFile file2 = new MockMultipartFile(
                "files", "file2.txt", "text/plain", "Another file.".getBytes());

        uploadBackupsRequest.addFromFiles(new MockMultipartFile[]{ file1, file2 });

        List<String> theBackupFileNames = uploadBackupsRequest.getUploadBackupRequests().stream()
                .map(UploadBackupRequest::getFileName).toList();
        assertThat(theBackupFileNames).contains("file1.txt");
        assertThat(theBackupFileNames).contains("file2.txt");
    }
}