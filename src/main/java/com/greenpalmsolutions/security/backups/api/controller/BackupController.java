package com.greenpalmsolutions.security.backups.api.controller;

import com.greenpalmsolutions.security.backups.api.behavior.CreateBackup;
import com.greenpalmsolutions.security.backups.api.behavior.FindBackup;
import com.greenpalmsolutions.security.backups.api.model.BackupDetails;
import com.greenpalmsolutions.security.backups.api.model.CreateBackupsResponse;
import com.greenpalmsolutions.security.backups.api.model.BackupRequests;
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

// TODO: test
@RestController
@RequiredArgsConstructor
public class BackupController {

    private final CreateBackup createBackup;
    private final FindBackup findBackup;

    @PostMapping("/v1/backups")
    public ResponseEntity<CreateBackupsResponse> createBackupsFromFiles(@RequestParam("files") MultipartFile[] files) {
        return new ResponseEntity<>(createBackup.createBackupForRequests(new BackupRequests().addFromFiles(files)),
                HttpStatus.CREATED);
    }

    @GetMapping("/v1/backups")
    public ResponseEntity<byte[]> downloadBackup(@RequestParam("file-path") String filePath) {
        BackupDetails backupDetails = findBackup.findBackupForFile(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + backupDetails.getFileName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(backupDetails.getFileContent());
    }
}
