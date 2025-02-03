package com.greenpalmsolutions.security.backups.internal;

import com.greenpalmsolutions.security.accounts.api.behavior.FindCurrentAccount;
import com.greenpalmsolutions.security.backups.api.behavior.DownloadBackups;
import com.greenpalmsolutions.security.backups.api.behavior.FindBackupFileNames;
import com.greenpalmsolutions.security.backups.api.model.*;
import com.greenpalmsolutions.security.backups.api.behavior.UploadBackups;
import com.greenpalmsolutions.security.files.api.behavior.DownloadFiles;
import com.greenpalmsolutions.security.files.api.behavior.UploadFile;
import com.greenpalmsolutions.security.files.api.model.UploadFileRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class BackupService implements UploadBackups, DownloadBackups, FindBackupFileNames {

    @Value("${app.file-storage.local.location}")
    private String FILE_STORAGE_LOCATION;

    private final BackupRepository backupRepository;
    private final UploadFile uploadFile;
    private final DownloadFiles downloadFiles;
    private final FindCurrentAccount findCurrentAccount;

    @Transactional
    @Override
    public UploadBackupsResponse uploadBackupsForRequest(UploadBackupsRequest requests) {
        final String USER_ID = findCurrentAccount.getUserIdForCurrentAccount();
        long userStorageInBytes = backupRepository.findStorageForUserInBytes(USER_ID);
        final String DIRECTORY_PATH = FILE_STORAGE_LOCATION + '/'
                + findCurrentAccount.getUserIdForCurrentAccount();
        for (UploadBackupRequest request : requests.getUploadBackupRequests()) {
            if (userStorageInBytes + request.getContentLengthInBytes() > 10000000000L) {
                throw new RuntimeException("Maximum storage space of 10GB cannot be surpassed for the user");
            }

            final String FILE_PATH = DIRECTORY_PATH + '/' + request.getFileNameWithoutExtension() + ".zip";
            createBackupFileFor(FILE_PATH, request.getFileContents());
            createBackupRecordFor(FILE_PATH, request);
            userStorageInBytes += request.getContentLengthInBytes();
        }
        return new UploadBackupsResponse(userStorageInBytes);
    }

    private void createBackupFileFor(String filePath, byte[] fileContents) {
        uploadFile.uploadFileForRequest(new UploadFileRequest()
                .withFileContents(fileContents)
                .withFilePath(filePath));
    }

    private void createBackupRecordFor(String filePath, UploadBackupRequest request) {
        Backup backup = new Backup();
        backup.setFilePath(filePath);
        backup.setFileExtension(request.getFileExtension());
        backup.setFileSizeInBytes(request.getContentLengthInBytes());
        backup.setUserId(findCurrentAccount.getUserIdForCurrentAccount());
        backupRepository.save(backup);
    }

    @Override
    public byte[] downloadBackupsForRequest(DownloadBackupsRequest request) {
        final String USER_ID = findCurrentAccount.getUserIdForCurrentAccount();
        final String FILE_PATH = FILE_STORAGE_LOCATION + '/'
                + USER_ID;

        final List<String> FILE_PATHS = request.getBackupRequests().stream().map(backup ->
                FILE_PATH + '/' + backup.getFileName())
                .toList();

        return downloadFiles.downloadZipForFilePaths(FILE_PATHS);
    }

    @Override
    public List<String> findFileNames() {
        return backupRepository.findByUserId(findCurrentAccount.getUserIdForCurrentAccount())
                .stream().map(Backup::getOriginalFileName)
                .toList();
    }
}
