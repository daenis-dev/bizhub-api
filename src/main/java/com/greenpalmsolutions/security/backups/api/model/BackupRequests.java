package com.greenpalmsolutions.security.backups.api.model;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: test
@Getter
public class BackupRequests {

    private final List<BackupRequest> backupRequests = new ArrayList<>();

    public BackupRequests addFromFiles(MultipartFile[] files) {
        for (MultipartFile file : files) {
            try {
                backupRequests.add(new BackupRequest().withFileName(file.getOriginalFilename()).withFileContents(file.getBytes()));
            } catch (IOException ex) {
                throw new RuntimeException("An error occurred while backing up the file", ex);
            }
        }
        return this;
    }
}
