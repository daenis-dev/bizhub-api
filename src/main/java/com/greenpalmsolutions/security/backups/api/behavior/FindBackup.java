package com.greenpalmsolutions.security.backups.api.behavior;

import com.greenpalmsolutions.security.backups.api.model.BackupDetails;

public interface FindBackup {

    BackupDetails findBackupForFile(String filePath);
}
