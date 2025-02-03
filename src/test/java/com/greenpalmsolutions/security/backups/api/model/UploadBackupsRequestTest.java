package com.greenpalmsolutions.security.backups.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void doesNotAddIfFileSizeInBytesIsTooLargeForIntegerDataType() {
        MockMultipartFile file1 = new MockMultipartFile(
                "files", "file1.txt", "text/plain", new byte[2]) {
            @Override
            public long getSize() {
                return 2147483647L + 4;
            }
        };

        InvalidRequestException theException = assertThrows(InvalidRequestException.class,
                () -> uploadBackupsRequest.addFromFiles(new MultipartFile[]{file1}));

        assertThat(theException.getMessage()).isEqualTo(
                "Document is too large - please submit a document that is under 2 GB");
    }
}