package com.greenpalmsolutions.security.backups.api.controller;

import com.greenpalmsolutions.security.backups.api.behavior.UploadBackups;
import com.greenpalmsolutions.security.backups.api.behavior.DownloadBackup;
import com.greenpalmsolutions.security.backups.api.model.BackupDetails;
import com.greenpalmsolutions.security.backups.api.model.DownloadBackupRequest;
import com.greenpalmsolutions.security.backups.api.model.UploadBackupsResponse;
import com.greenpalmsolutions.security.backups.api.model.UploadBackupsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class BackupController {

    private final UploadBackups uploadBackups;
    private final DownloadBackup downloadBackup;

    @PostMapping("/v1/backups")
    public ResponseEntity<UploadBackupsResponse> createBackupsFromFiles(@RequestParam("files") MultipartFile[] files) {
        return new ResponseEntity<>(uploadBackups.uploadBackupsForRequest(new UploadBackupsRequest().addFromFiles(files)),
                HttpStatus.CREATED);
    }

    @GetMapping("/v1/backups")
    public ResponseEntity<byte[]> downloadBackup(@RequestParam("file-name") String fileName) {
        BackupDetails backupDetails = downloadBackup.downloadForRequest(new DownloadBackupRequest().withFileName(fileName));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + backupDetails.getFileName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(backupDetails.getFileContent());
    }
}
