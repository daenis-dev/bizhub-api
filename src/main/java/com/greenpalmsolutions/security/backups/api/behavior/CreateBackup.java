package com.greenpalmsolutions.security.backups.api.behavior;

import com.greenpalmsolutions.security.backups.api.model.CreateBackupsResponse;
import com.greenpalmsolutions.security.backups.api.model.BackupRequests;

public interface CreateBackup {

    CreateBackupsResponse createBackupForRequests(BackupRequests requests);
}
