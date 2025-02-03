package com.greenpalmsolutions.security.backups.api.model;

import com.greenpalmsolutions.security.core.errorhandling.InvalidRequestException;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UploadBackupsRequest {

    private final List<UploadBackupRequest> uploadBackupRequests = new ArrayList<>();

    public UploadBackupsRequest addFromFiles(MultipartFile[] files) {
        for (MultipartFile file : files) {
            try {
                if (file.getSize() > 2147483647) {
                    throw new InvalidRequestException(
                            "Document is too large - please submit a document that is under 2 GB");
                }
                uploadBackupRequests.add(new UploadBackupRequest()
                        .withFileName(file.getOriginalFilename())
                        .withFileContents(file.getBytes()));
            } catch (IOException ex) {
                throw new RuntimeException("An error occurred while backing up the file", ex);
            }
        }
        return this;
    }
}
