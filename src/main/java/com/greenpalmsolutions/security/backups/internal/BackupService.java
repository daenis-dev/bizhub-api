package com.greenpalmsolutions.security.backups.internal;

import com.greenpalmsolutions.security.accounts.api.behavior.FindCurrentAccount;
import com.greenpalmsolutions.security.backups.api.behavior.DownloadBackup;
import com.greenpalmsolutions.security.backups.api.behavior.DownloadBackups;
import com.greenpalmsolutions.security.backups.api.behavior.FindBackupFileNames;
import com.greenpalmsolutions.security.backups.api.model.*;
import com.greenpalmsolutions.security.backups.api.behavior.UploadBackups;
import com.greenpalmsolutions.security.files.api.behavior.DownloadFile;
import com.greenpalmsolutions.security.files.api.behavior.DownloadFilesAsZip;
import com.greenpalmsolutions.security.files.api.behavior.UploadFile;
import com.greenpalmsolutions.security.files.api.model.UploadFileRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class BackupService implements UploadBackups, DownloadBackup, DownloadBackups, FindBackupFileNames {

    @Value("${app.file-storage.local.location}")
    private String FILE_STORAGE_LOCATION;

    private final BackupRepository backupRepository;
    private final UploadFile uploadFile;
    private final DownloadFile downloadFile;
    private final DownloadFilesAsZip downloadFilesAsZip;
    private final FindCurrentAccount findCurrentAccount;

    @Transactional
    @Override
    public UploadBackupsResponse uploadBackupsForRequest(UploadBackupsRequest requests) {
        long totalBytes = 0;
        final String DIRECTORY_PATH = FILE_STORAGE_LOCATION + '/'
                + findCurrentAccount.getUserIdForCurrentAccount();
        for (UploadBackupRequest request : requests.getUploadBackupRequests()) {
            if (totalBytes + request.getContentLengthInBytes() > 10000000000L) {
                throw new RuntimeException("Backup cannot exceed 10 GB");
            }

            final String FILE_PATH = DIRECTORY_PATH + '/' + request.getFileNameWithoutExtension() + ".zip";
            createBackupFileFor(FILE_PATH, request.getFileContents());
            createBackupRecordFor(FILE_PATH, request.getFileExtension());
            totalBytes += request.getContentLengthInBytes();
        }
        return new UploadBackupsResponse(totalBytes);
    }

    private void createBackupFileFor(String filePath, byte[] fileContents) {
        uploadFile.uploadFileForRequest(new UploadFileRequest()
                .withFileContents(fileContents)
                .withFilePath(filePath));
    }

    private void createBackupRecordFor(String filePath, String fileExtension) {
        Backup backup = new Backup();
        backup.setFilePath(filePath);
        backup.setFileExtension(fileExtension);
        backup.setUserId(findCurrentAccount.getUserIdForCurrentAccount());
        backupRepository.save(backup);
    }

    // TODO: IT
    @Override
    public byte[] downloadBackupsForRequest(DownloadBackupsRequest request) {
        final String USER_ID = findCurrentAccount.getUserIdForCurrentAccount();
        final String FILE_PATH = FILE_STORAGE_LOCATION + '/'
                + USER_ID;

        final List<String> FILE_PATHS = request.getBackupRequests().stream().map(backup ->
                FILE_PATH + '/' + backup.getFileNameWithoutExtension() + ".zip")
                .toList();

        return downloadFilesAsZip.downloadFilesForFilePaths(FILE_PATHS);
    }

    @Override
    public BackupDetails downloadForRequest(DownloadBackupRequest request) {
        final String USER_ID = findCurrentAccount.getUserIdForCurrentAccount();
        final String FILE_PATH = FILE_STORAGE_LOCATION + '/'
                + USER_ID + '/'
                + request.getFileNameWithoutExtension() + ".zip";

        Backup backup = findBackupForFilePath(FILE_PATH, USER_ID);

        return new BackupDetails(
                backup.getOriginalFileName(),
                downloadFile.downloadFileWithFilePath(backup.getFilePath()));
    }

    private Backup findBackupForFilePath(String filePath, String userId) {
        return backupRepository.findByFilePathAndUserId(filePath, userId)
                .orElseThrow(() -> new RuntimeException("An error occurred while retrieving the backup"));
    }

    @Override
    public List<String> findFileNames() {
        return backupRepository.findByUserId(findCurrentAccount.getUserIdForCurrentAccount())
                .stream().map(Backup::getOriginalFileName)
                .toList();
    }
}
