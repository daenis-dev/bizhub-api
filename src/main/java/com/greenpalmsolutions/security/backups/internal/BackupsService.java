package com.greenpalmsolutions.security.backups.internal;

import com.greenpalmsolutions.security.accounts.api.behavior.FindCurrentAccount;
import com.greenpalmsolutions.security.backups.api.behavior.FindBackup;
import com.greenpalmsolutions.security.backups.api.model.BackupDetails;
import com.greenpalmsolutions.security.backups.api.model.CreateBackupsResponse;
import com.greenpalmsolutions.security.backups.api.model.BackupRequest;
import com.greenpalmsolutions.security.backups.api.behavior.CreateBackups;
import com.greenpalmsolutions.security.backups.api.model.BackupRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

// TODO: IT
@Service
@RequiredArgsConstructor
class BackupsService implements CreateBackups, FindBackup {

    private final BackupRepository backupRepository;
    private final FindCurrentAccount findCurrentAccount;

    @Override
    public CreateBackupsResponse createBackupForRequests(BackupRequests requests) {
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
            backup.setFilePath("src/main/resources/storage/" + request.getFileName()
                    .replaceAll("\\.[^.]+$", "") + ".zip");
            backup.setFileExtension(request.getFileName()
                    .replaceAll("^.*\\.", ""));
            backup.setCompressed(true);
            backup.setUserId(findCurrentAccount.getUserIdForCurrentAccount());
            backupRepository.save(backup);
        }
        return new CreateBackupsResponse(totalBytes);
    }

    @Override
    public BackupDetails findBackupForFile(String filePath) {
        Backup backup = backupRepository.findByFilePathAndUserId(
                "src/main/resources/storage/" + filePath, findCurrentAccount.getUserIdForCurrentAccount())
                .orElseThrow(() -> new RuntimeException("An error occurred while retrieving the backup"));
        try {
            return new BackupDetails(backup.getOriginalFileName(), extractFileFromZip(Paths.get("src/main/resources/storage/" + filePath)));
        } catch (IOException ex) {
            throw new RuntimeException("Error while retrieving backup", ex);
        }
    }

    private byte[] extractFileFromZip(Path zipFilePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(zipFilePath.toFile());
             ZipInputStream zis = new ZipInputStream(fis)) {

            ZipEntry zipEntry = zis.getNextEntry();
            if (zipEntry != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = zis.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, length);
                }
                return byteArrayOutputStream.toByteArray();
            } else {
                throw new FileNotFoundException("No entries found in the zip file.");
            }
        }
    }

}
