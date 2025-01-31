package com.greenpalmsolutions.security.backups.internal;

import com.greenpalmsolutions.security.accounts.api.behavior.FindCurrentAccount;
import com.greenpalmsolutions.security.backups.api.model.BackupDetails;
import com.greenpalmsolutions.security.backups.api.model.BackupRequest;
import com.greenpalmsolutions.security.backups.api.behavior.CreateBackup;
import com.greenpalmsolutions.security.backups.api.model.BackupRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

// TODO: IT
@Service
@RequiredArgsConstructor
class BackupService implements CreateBackup {

    private final BackupRepository backupRepository;
    private final FindCurrentAccount findCurrentAccount;

    @Override
    public BackupDetails createBackupForRequests(BackupRequests requests) {
        int totalBytes = 0;
        for (BackupRequest request : requests.getBackupRequests()) {
            try {
                byte[] fileContent = request.getFileContents();
                Path storagePath = Paths.get("src/main/resources/storage/" + request.getFileName() + ".zip");

                Files.createDirectories(storagePath.getParent());

                try (FileOutputStream fos = new FileOutputStream(storagePath.toFile());
                     ZipOutputStream zos = new ZipOutputStream(fos)) {

                    ZipEntry zipEntry = new ZipEntry(request.getFileName());
                    zos.putNextEntry(zipEntry);

                    zos.write(fileContent);
                    zos.closeEntry();
                }

                totalBytes += fileContent.length;

            } catch (IOException ex) {
                throw new RuntimeException("An error occurred while backing up the file", ex);
            }

            Backup backup = new Backup();
            backup.setFilePath("src/main/resources/storage/" + request.getFileName() + ".zip");
            backup.setCompressed(true);
            backup.setUserId(findCurrentAccount.getUserIdForCurrentAccount());
            backupRepository.save(backup);
        }
        return new BackupDetails(totalBytes);
    }
}
