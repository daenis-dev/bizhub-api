package com.greenpalmsolutions.security.backups.api.behavior;

import com.greenpalmsolutions.security.backups.api.model.BackupDetails;
import com.greenpalmsolutions.security.backups.api.model.DownloadBackupRequest;

public interface DownloadBackup {

    BackupDetails downloadForRequest(DownloadBackupRequest request);
}
