package com.greenpalmsolutions.security.backups.api.controller;

import com.greenpalmsolutions.security.backups.api.behavior.DownloadBackups;
import com.greenpalmsolutions.security.backups.api.behavior.FindBackupFileNames;
import com.greenpalmsolutions.security.backups.api.behavior.UploadBackups;
import com.greenpalmsolutions.security.backups.api.model.*;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BackupController {

    private final UploadBackups uploadBackups;
    private final DownloadBackups downloadBackups;
    private final FindBackupFileNames findBackupFileNames;

    @PostMapping("/v1/backups")
    public ResponseEntity<UploadBackupsResponse> createBackupsFromFiles(@RequestParam("files") MultipartFile[] files) {
        return new ResponseEntity<>(uploadBackups.uploadBackupsForRequest(new UploadBackupsRequest().addFromFiles(files)),
                HttpStatus.CREATED);
    }

    @GetMapping("/v1/backups")
    public ResponseEntity<byte[]> downloadBackups(@RequestParam("file-names") String fileNames) {
        byte[] backupsAsZipFile = downloadBackups.downloadBackupsForRequest(
                new DownloadBackupsRequest().addFromListOfFileNamesAsString(fileNames));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=checkers-backup.zip");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(backupsAsZipFile);
    }

    @GetMapping("/v1/backups/file-names")
    public ResponseEntity<List<String>> findBackupFileNames() {
        return ResponseEntity.ok(findBackupFileNames.findFileNames());
    }
}
