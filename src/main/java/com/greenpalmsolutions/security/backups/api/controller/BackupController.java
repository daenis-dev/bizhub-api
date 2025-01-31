package com.greenpalmsolutions.security.backups.api.controller;

import com.greenpalmsolutions.security.backups.api.behavior.CreateBackup;
import com.greenpalmsolutions.security.backups.api.model.BackupDetails;
import com.greenpalmsolutions.security.backups.api.model.BackupRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// TODO: test
@RestController
@RequiredArgsConstructor
public class BackupController {

    private final CreateBackup createBackup;

    @PostMapping("/v1/backups")
    public ResponseEntity<BackupDetails> createBackupsFromFiles(@RequestParam("files") MultipartFile[] files) {
        return new ResponseEntity<>(createBackup.createBackupForRequests(new BackupRequests().addFromFiles(files)),
                HttpStatus.CREATED);
    }
}
